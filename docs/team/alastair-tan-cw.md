### Project: CLinkedin

Given below are my contributions to the project.

* **New Feature**: Implemented 2 tag related commands (`tag rename` and `tag list`).
    * **What it does**:
        * `tag rename`: Allows users to modify an existing tag's name. It attaches this name change to every contact holding the tag while perfectly preserving any custom color previously assigned to it.
        * `tag list`: Displays a consolidated view of all unique tags currently existing in the application.
    * **Justification**: Tags are heavily utilized in CLinkedin for categorizing contacts. Providing a way to rename tags prevents users from having to manually delete and re-assign tags to dozens of contacts just to fix a typo. Preserving the color maintains the user's customized visual organization.
    * **Highlights**: Implementing `tag rename` required careful navigation of Java's object memory and variable scope. It involved retrieving the actual tag instance directly from the `Model` (using streams and `.orElseThrow()`) to extract the old tag color.

* **Enhancements to existing features**: Improved Data Validation and Error Handling for the `Add` and `Edit` commands.
    * **Duplicate Phone Handling**: Upgraded the validation logic in the `AddCommand` and `EditCommand` to strictly reject duplicate phone numbers. This prevents overlapping contact details and maintains data integrity.
    * **Missing Required Fields Error**: Improved the `AddCommandParser` to state exactly which required fields are missing from the user's input, replacing the generic "Invalid command format" error. This improves the user experience by providing actionable feedback.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2526-s2.github.io/tp-dashboard/?search=alastair-tan-cw&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

* **Documentation**:
    * **Developer Guide (DG)**:
        * Checked on Documentation Guide to ensure formatting consistency.
        * Standardized UML Sequence Diagrams across the architecture sections.
        * Authored the implementation details for the `TagRenameCommand`, including the step-by-step logic for retrieving original tag colors and the necessity of safely querying the central tag list.

* **Community**:
    * Conducted PR reviews to ensure alignment with our goals.