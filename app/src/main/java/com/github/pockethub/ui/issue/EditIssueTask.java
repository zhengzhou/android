/*
 * Copyright (c) 2015 PocketHub
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pockethub.ui.issue;

import android.accounts.Account;

import com.alorma.github.sdk.bean.dto.request.IssueRequest;
import com.alorma.github.sdk.bean.dto.response.Issue;
import com.alorma.github.sdk.bean.dto.response.Repo;
import com.alorma.github.sdk.services.issues.EditIssueClient;
import com.github.pockethub.R;
import com.github.pockethub.core.issue.IssueStore;
import com.github.pockethub.ui.DialogFragmentActivity;
import com.github.pockethub.ui.ProgressDialogTask;
import com.github.pockethub.util.InfoUtils;
import com.google.inject.Inject;

/**
 * Task to edit an entire issue
 */
public class EditIssueTask extends ProgressDialogTask<Issue> {

    @Inject
    private IssueStore store;

    private final Repo repositoryId;

    private final int issueNumber;

    private final IssueRequest issue;

    /**
     * Create task to edit a milestone
     *
     * @param activity
     * @param repositoryId
     * @param issueNumber
     * @param issue
     */
    public EditIssueTask(final DialogFragmentActivity activity,
            final Repo repositoryId, final int issueNumber, final IssueRequest issue) {
        super(activity);

        this.repositoryId = repositoryId;
        this.issueNumber = issueNumber;
        this.issue = issue;
    }

    @Override
    protected Issue run(Account account) throws Exception {
        return new EditIssueClient(InfoUtils.createIssueInfo(repositoryId, issueNumber), issue).observable().toBlocking().first();
    }

    /**
     * Edit issue
     *
     * @return this task
     */
    public EditIssueTask edit() {
        showIndeterminate(R.string.updating_issue);

        execute();
        return this;
    }
}
