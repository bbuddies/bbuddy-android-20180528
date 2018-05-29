package com.odde.bbuddy.budget.view;

import com.odde.bbuddy.common.validation.Violation;

public interface BudgetView {
    void showError(Violation violation);
}
