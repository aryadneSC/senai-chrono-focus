package com.example.chronofocus.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.chronofocus.R;
import com.example.chronofocus.databinding.ActivitySessionBinding;
import com.example.chronofocus.model.SessionStatus;
import com.example.chronofocus.utils.TimerUtils;
import com.example.chronofocus.viewmodels.SessionViewModel;

import java.util.concurrent.atomic.AtomicBoolean;

public class SessionActivity extends AppCompatActivity {
    private ActivitySessionBinding binding;
    private SessionViewModel viewModel;
    private AppCompatActivity context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySessionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this, ViewModelProvider.Factory.from(SessionViewModel.initializer)).get(SessionViewModel.class);

        binding.tvTitle.setText(R.string.sessao);
        changeButtonsVisibility(SessionStatus.INACTIVE);
        binding.btnBack.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        initClickListeners();
        observeViewModel();

        viewModel.updateSessionData();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (hasSubjects.get()) {
                    setEnabled(false);
                    if (timer != null) {

                        isPaused = true;
                        timer.cancel();
                        changeButtonsVisibility(SessionStatus.PAUSED);
                        viewModel.pauseAndSave(() -> runOnUiThread(() -> finish()));
                    } else {
                        finish();
                    }
                } else {
                    finish();
                }
            }
        });
    }

    private void initClickListeners() {
        binding.btnStart.setOnClickListener(v -> onStartTimer());
        binding.btnFinishPrimary.setOnClickListener(v -> onFinishSession());
        binding.btnFinishSecondary.setOnClickListener(v -> onFinishSession());
        binding.btnPause.setOnClickListener(v -> onPauseTimer(false));
        binding.btnRestart.setOnClickListener(v -> onRestartTimer());
        binding.btnResume.setOnClickListener(v -> onResumeTimer());
        binding.btnNextMateria.setOnClickListener(v -> onNextPressed());
    }

    AtomicBoolean hasSubjects = new AtomicBoolean(false);

    private void observeViewModel() {
        viewModel.getState().observe(this, state -> {
            hasSubjects.set(state == SessionViewModel.SessionState.SUBJECTS_READY);
        });

        viewModel.getCurrentMateriaName().observe(this, current -> {
            binding.tvMateria.setText(current);
        });

        viewModel.getNextMateriaName().observe(this, next -> {
            binding.containerNextMateria.setVisibility(next.isEmpty() ? View.GONE : View.VISIBLE);
            binding.tvNextSubject.setText(next);
        });

        viewModel.getFormattedPausedOrInactive().observe(this, formattedPause -> {
            if (!formattedPause.isEmpty()) {
                binding.tvTimer.setText(formattedPause);
            }
        });
    }

    private void hideAllButtons() {
        binding.btnStart.setVisibility(View.GONE);
        binding.btnRestart.setVisibility(View.GONE);
        binding.btnNextMateria.setVisibility(View.GONE);
        binding.btnFinishPrimary.setVisibility(View.GONE);
        binding.btnPause.setVisibility(View.GONE);
        binding.btnResume.setVisibility(View.GONE);
        binding.btnFinishSecondary.setVisibility(View.GONE);
    }
    private void changeButtonsVisibility(SessionStatus sessionStatus) {
        hideAllButtons();
        switch (sessionStatus) {
            case INACTIVE:
                binding.btnStart.setVisibility(View.VISIBLE);
                break;
            case STARTED:
                binding.btnFinishPrimary.setVisibility(View.VISIBLE);
                binding.btnPause.setVisibility(View.VISIBLE);
                break;
            case FINISHED:
                binding.btnRestart.setVisibility(View.VISIBLE);
                binding.btnNextMateria.setVisibility(View.VISIBLE);
                break;
            case PAUSED:
                binding.btnResume.setVisibility(View.VISIBLE);
                binding.btnFinishSecondary.setVisibility(View.VISIBLE);
                break;
        }
    }
    private CountDownTimer timer;
    private boolean isPaused = false;
    private long endTime;

    private void fetchEndTime() {
        endTime = viewModel.updateAndGetEndTimeMillis();
    }
    private void onCountTick() {
        long remaining = endTime - SystemClock.elapsedRealtime();
        if(!isPaused) {
            binding.tvTimer.setText(TimerUtils.millisToFormattedTimeString(remaining, viewModel.getBaseMillis()));
        }
    }

    private void onCountFinish() {
        Toast.makeText(context, R.string.sess_o_finalizada, Toast.LENGTH_SHORT).show();
        onFinishSession();
    }

    private CountDownTimer setupTimer() {
        fetchEndTime();
        long remaining = endTime - SystemClock.elapsedRealtime();

        return new CountDownTimer(remaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                onCountTick();
            }

            @Override
            public void onFinish() {
                onCountFinish();
            }
        };
    }
    private void onStartTimer() {
        changeButtonsVisibility(SessionStatus.STARTED);
        isPaused = false;
        timer = setupTimer();
        timer.start();
        viewModel.notifyStarted();
    }
    private void onPauseTimer(boolean quietly) {
        changeButtonsVisibility(SessionStatus.PAUSED);
        timer.cancel();
        viewModel.notifyPaused(quietly);
        isPaused = true;
    }

    private void onResumeTimer() {
        onStartTimer();
    }

    private void onFinishSession() {
        timer.cancel();
        changeButtonsVisibility(SessionStatus.FINISHED);
        viewModel.notifyEnded(() -> runOnUiThread(() -> {
            binding.btnRestart.setEnabled(true);
        }));
    }

    private void onRestartTimer() {
        viewModel.resetSession();
        onStartTimer();
    }

    private void onNextPressed() {
        viewModel.notifyNext();
        changeButtonsVisibility(SessionStatus.INACTIVE);
    }


}