package com.odde.bbuddy.authentication.view;

import com.odde.bbuddy.common.validation.Violation;

public interface AuthenticationView {
    void showError(Violation violation);
}
