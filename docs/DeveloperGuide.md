---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# CLinkedin Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />

The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object) and all `Tag` objects (which are contained in a `UniqueTagList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* manages the relationship between `Person` objects and `Tag` objects, where each `Person` can have zero or more `Tag` objects, and each `Tag` is stored in the `UniqueTagList`.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `CLinkedinStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.clinkedin.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Contact management

#### Finding contacts by company

The `findcom` command allows users to find contacts whose company matches one or more keywords.

When the command is executed, the system first checks whether any company keyword was provided. If the input is empty, the command fails and an error message is shown.

If input is provided, the system updates the filtered contact list to show contacts whose company matches any of the given keywords, case-insensitively.

The following activity diagram illustrates the decision flow of the `findcom` command:

<puml src="diagrams/company/FindComActivityDiagram.puml" alt="FindComActivityDiagram" />

The following sequence diagram illustrates how the `findcom` command is handled by the system components:

<puml src="diagrams/company/FindComSequenceDiagram.puml" alt="company/FindComSequenceDiagram" />

#### Sorting contacts by company

The `sortcom` command allows users to sort the currently displayed contact list alphabetically by company name.

<box type="info" seamless>

**Note:** The sorting operation is applied only to the **filtered contact list** (i.e., the currently displayed contacts), not the entire dataset.

</box>

When the command is executed, the system sorts the filtered contact list by company name in a case-insensitive manner. Contacts without a company are treated as having an empty value and will appear at the top of the displayed list. The sorted list is then shown to the user together with a success message.

The following activity diagram illustrates the flow of the `sortcom` command:

<puml src="diagrams/company/SortComActivityDiagram.puml" alt="SortComActivityDiagram" />

The following sequence diagram illustrates how the `sortcom` command is handled by the system components:

<puml src="diagrams/company/SortComSequenceDiagram.puml" alt="SortComSequenceDiagram" />

#### Contact restoration

The `restore` command allows users to restore a previously deleted contact from the deleted contacts list.

Users can use the `deleted` command to view the list of deleted contacts and identify the correct index for restoration.

Contacts can only be restored within 7 days of deletion.

When the command is executed, the system first checks whether the provided index is valid in the deleted contacts list. If the index is invalid, the command fails and an error message is shown.

If the index is valid, the system checks whether restoring the contact would cause a conflict, such as a duplicate phone number or an already existing contact. If such a conflict exists, the command fails.

If restoration is allowed, the contact is added back to the main contact list and removed from the deleted contacts list. Tags whose names no longer exist will not be restored. Tags whose names still exist will be restored using their current tag definitions.

The following activity diagram illustrates the decision flow of the `restore` command:

<puml src="diagrams/RestoreActivityDiagram.puml" alt="RestoreActivityDiagram" />

The following sequence diagram illustrates how the `restore` command is handled by the system components:

<puml src="diagrams/RestoreSequenceDiagram.puml" alt="RestoreSequenceDiagram" />

#### Finding contacts by tag

The `tag show` command allows users to find contacts that have a specific tag.

Users can input a single tag to filter the list of contacts.

When the command is executed, the system first checks whether any tag keyword was provided. If the input is empty, the command fails and an error message is shown.

If input is provided, the system checks if the tag exists. If it doesn’t, the command fails and an error message is shown.

If the tag exists, the system creates a TagContainsKeywordPredicate and updates the contact list to show contacts who have that specific tag.

The following activity diagram illustrates the decision flow of the tag show command:

<puml src="diagrams/tag/TagShowActivityDiagram.puml" alt="TagShowActivityDiagram" />

The following sequence diagram illustrates how the tag show command is handled by the system components:

<puml src="diagrams/tag/TagShowSequenceDiagram.puml" alt="TagShowSequenceDiagram" />

### Tag management
#### Tag creation

The `tag create` command allows users to create a new tag with an optional colour.

When the command is executed, the system first checks whether a tag with the same name already exists. Since duplicate tag names are not allowed, the command fails if the tag already exists.

If the tag name is valid and no duplicate is found, the system checks whether a colour was provided. If no colour is specified, a default colour is assigned. The tag is then added to the tag list.

The following activity diagram illustrates the decision flow of the `tag create` command:

<puml src="diagrams/tag/TagCreateActivityDiagram.puml" alt="TagCreateActivityDiagram" />

The following sequence diagram illustrates how the `tag create` command is handled by the system components:

<puml src="diagrams/tag/TagCreateSequenceDiagram.puml" alt="TagCreateSequenceDiagram" />

#### Tag deletion

The `tag delete` command allows users to delete an existing tag.

When the command is executed, the system first checks whether the specified tag exists. If the tag does not exist, the command fails and an error message is shown.

If the tag exists, the tag is removed from the tag list. The system then removes that tag from all contacts currently using it, while leaving all other tags on those contacts unchanged.

The following activity diagram illustrates the decision flow of the `tag delete` command:

<puml src="diagrams/tag/TagDeleteActivityDiagram.puml" alt="TagDeleteActivityDiagram" />

The following sequence diagram illustrates how the `tag delete` command is handled by the system components:

<puml src="diagrams/tag/TagDeleteSequenceDiagram.puml" alt="TagDeleteSequenceDiagram" />

#### Tag assignment

The `tag assign` command allows users to assign an existing tag to one or more contacts at once.

When the command is executed, the system first verifies that the specified tag exists in the tag list. If it does, it checks that all provided contact indices are valid. If any index is invalid, the command aborts to prevent partial execution. Upon successful validation, the tag is added to the specified contacts, and the model is updated.

The following activity diagram illustrates the decision flow:
<puml src="diagrams/tag/TagAssignActivityDiagram.puml" alt="TagAssignActivityDiagram" />

The following sequence diagram illustrates the execution:
<puml src="diagrams/tag/TagAssignSequenceDiagram.puml" alt="TagAssignSequenceDiagram" />

#### Tag unassignment

The `tag unassign` command allows users to unassign an existing tag to one or more contacts at once.

When the command is executed, the system first verifies that the specified tag exists in the tag list. If it does, it checks that all provided contact indices are valid and has the specified tag. If any index is invalid or one or more user does not have the tag, the command aborts to prevent partial execution. Upon successful validation, the tag is removed from the specified contacts, and the model is updated.

The following activity diagram illustrates the decision flow:
<puml src="diagrams/tag/TagUnassignActivityDiagram.puml" alt="TagUnassignActivityDiagram" />

The following sequence diagram illustrates the execution:
<puml src="diagrams/tag/TagUnassignSequenceDiagram.puml" alt="TagUnassignSequenceDiagram" />

#### Tag rename

The `tag rename` commands allow users to modify the name of an existing tag, while also replacing the tag attached to the respective contacts.

Because tags are immutable objects in this architecture, modifying a tag requires replacing it. The system first validates that the old tag exists. It then creates the new tag in the global `CLinkedin` tag list and attach it to the contacts containing the old tag through iteration. Finally, it removes the old tag from the tag list.

The following activity diagram illustrates the decision flow:
<puml src="diagrams/tag/TagRenameActivityDiagram.puml" alt="TagRenameActivityDiagram" />

The sequence diagram below illustrates the execution:
<puml src="diagrams/tag/TagRenameSequenceDiagram.puml" alt="TagRenameSequenceDiagram" />

#### Tag color

The `tag color` command allows users to add a color to an existing tag.

When the command is executed, the system first checks whether the color is valid. If the color is not valid, the command fails and an error message is shown.

If the color is valid, the system then checks if the tag exists. If the tag does not exist, the command fails and an error message is shown.

If both the tag and color is valid, the color is then added to the tag and the model is updated.

The following activity diagram illustrates the decision flow:

<puml src="diagrams/tag/TagColorActivityDiagram.puml" alt="TagColorActivityDiagram" />

The sequence diagram below illustrates the execution:

<puml src="diagrams/tag/TagColorSequenceDiagram.puml" alt="TagColorSequenceDiagram" />

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* NUS SOC students who collect many professional contacts (e.g. peers, seniors, alumni)
* comfortable with using command-line interfaces to manage contacts

**Value proposition**: Helps NUS SoC students manage and recall networking contacts quickly by providing a structured, command-line address book tailored for technical users.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                        | So that I can…​                                                                   |
|----------|--------------------------------------------|-----------------------------------------------------|-----------------------------------------------------------------------------------|
| `* * *`  | new user                                   | add a new contact with name, phone number and email | store and connect with them                                                       |
| `* * *`  | new user                                   | click on a contact link                             | view their social media page or company website                                   |
| `* * *`  | new user                                   | edit contact details                                | update information without deleting and recreating contacts                       |
| `* * *`  | new user                                   | receive clear error messages                        | correct mistakes when I input invalid commands                                    |
| `* * *`  | new user                                   | search a contact by name                            | find the person I’m connecting with quickly                                       |
| `* * *`  | new user                                   | view command instructions                           | quickly recall available commands and their valid formats                         |
| `* * *`  | organised user                             | add a tag to a contact                              | categorise a group of people together                                             |
| `* * *`  | organised user                             | create and delete my own tags                       | categorise contacts the way I like                                                |
| `* * *`  | organised user                             | list all contacts under a specific tag              | quickly see related connections and contacts                                      |
| `* * *`  | organised user                             | remove a tag from a contact                         | re-categorise people                                                              |
| `* * *`  | organised user                             | rename a tag                                        | keep my tagging system consistent                                                 |
| `* * *`  | organised user                             | sort contacts by company                            | view grouped workplace connections                                                |
| `* * *`  | organised user                             | view all existing tags                              | know how contacts are categorised                                                 |
| `* * *`  | networking-focused user                    | search for contacts in a specified company          | quickly find all my connections working at the company                            |
| `* *`    | new user                                   | add remarks to contacts                             | store more personal information (like hobbies/interests)                          |
| `* *`    | organised user                             | colour code my tags                                 | visually distinguish categories                                                   |
| `* *`    | expert user                                | search using partial keywords                       | avoid remembering exact spelling                                                  |
| `* *`    | expert user                                | see date when contact was added                     | recall when I met someone                                                         |
| `* *`    | expert user                                | search for contacts in multiple specified companies | view a combined list of connections from specified companies fast and efficiently |
| `* *`    | networking-focused user                    | add meaningful remarks about a contact              | recall conversations for networking purpose                                       |
| `*`      | user with many persons in the address book | find contacts by name                               | locate a person easily                                                            |
| `*`      | expert user                                | recover deleted contacts                            | restore accidentally removed contacts                                             |
| `*`      | expert user                                | tag multiple contacts at once                       | group people fast and efficiently                                                 |

### Use cases

(For all use cases below, the **System** is the `CLinkedin` and the **Actor** is the `user`, unless specified otherwise)

---
### **Use Case: Edit contact**

**Preconditions:**
* The application is running.
* The contact list is not empty.

**Guarantees:**
* If successful, the specified contact is updated.
* If unsuccessful, the contact list remains unchanged.

#### **MSS**

1. User requests to list contacts.
2. CLinkedin displays the list of contacts.
3. User requests to edit a contact by entering the edit command with the contact's index and the fields to be updated.
4. CLinkedin validates the provided index and updated field values.
5. CLinkedin updates the contact with the new details.
6. CLinkedin displays a success message with the edited contact's details.

   Use case ends.

#### **Extensions**

* 3a. The user provides an index that does not exist in the list.
    * 3a1. CLinkedin shows an error message that the index is invalid.

      Use case resumes at step 1.

* 3b. The user provides an invalid command format.
    * 3b1. CLinkedin shows an error message that the command format is invalid.

      Use case resumes at step 1.

* 4a. The updated field value is invalid.
    * 4a1. CLinkedin shows an error message indicating which field value is invalid.

      Use case resumes at step 1.

* 4b. The updated phone number already exists in another contact.
    * 4b1. CLinkedin shows an error message that the phone number already exists in the list.

      Use case resumes at step 1.

---

### **Use Case: Delete contact**

**Preconditions:**
* The application is running.
* The contact list is not empty.

**Guarantees:**
* If successful, the specified contact is removed from the contact list and stored in the deleted contacts list with a timestamp.
* If unsuccessful, the contact list remains unchanged.

#### **MSS**

1. User requests to list contacts.
2. CLinkedin displays the list of contacts.
3. User requests to delete a contact by entering the delete command with the contact's index.
4. CLinkedin validates that the provided index is valid.
5. CLinkedin removes the contact from the contact list and stores it in the deleted contacts list with a timestamp.
6. CLinkedin displays a success message that the contact has been deleted.

   Use case ends.

#### **Extensions**

* 3a. The user provides an index that does not exist in the list.
    * 3a1. CLinkedin shows an error message that the index is invalid.

      Use case resumes at step 1.

* 3b. The user provides an invalid command format.
    * 3b1. CLinkedin shows an error message that the command format is invalid.

      Use case resumes at step 1.

---

### **Use Case: Create tag**

**Preconditions:**
* The application is running.

**Guarantees:**
* If successful, a new tag is added to the tag list.
* If unsuccessful, the tag list remains unchanged.

#### **MSS**

1. User requests to create a tag with a name and an optional color.
2. CLinkedin validates the provided tag details.
3. CLinkedin creates the tag.
4. CLinkedin displays a success message.

   Use case ends.

#### **Extensions**

* 2a. The tag name already exists.
    * 2a1. CLinkedin shows an error message that the tag already exists.

      Use case resumes at step 1.

* 2b. The tag color format is invalid.
    * 2b1. CLinkedin shows an error message that the tag color format is invalid.

      Use case resumes at step 1.

---

### **Use Case: Modify tag**

**Preconditions:**
* The application is running.
* The tag already exists.

**Guarantees:**
* If successful, the specified tag is updated or removed accordingly.
* If unsuccessful, the tag list remains unchanged.

#### **MSS**

1. User requests to rename a tag.
2. CLinkedin validates the new tag name.
3. CLinkedin updates the tag name.
4. CLinkedin updates all contacts that contain the tag.
5. CLinkedin displays a success message.

   Use case ends.

#### **Extensions**

* 2a. The new tag name already exists.
    * 2a1. CLinkedin shows an error message that the tag name already exists.

      Use case resumes at step 1.

* 1a. User requests to change a tag's color.
    * 1a1. CLinkedin validates the new color value.
    * 1a2. CLinkedin updates the tag color.
    * 1a3. CLinkedin displays a success message.

      Use case ends.

* 1b. User requests to delete a tag.
    * 1b1. CLinkedin removes the tag from the tag list.
    * 1b2. CLinkedin removes the tag from all associated contacts.
    * 1b3. CLinkedin displays a success message.

      Use case ends.
---

### **Use Case: Assign or unassign tags for contacts**

**Preconditions:**
* The application is running.
* The tag already exists.

**Guarantees:**
* If successful, the specified contacts are updated with the tag assignment changes.
* If unsuccessful, no contact-tag assignments are changed.

#### **MSS**

1. User requests to assign a tag to one or more contacts.
2. CLinkedin validates the provided contact indexes and tag name.
3. CLinkedin assigns the tag to the specified contacts.
4. CLinkedin displays a success message.

   Use case ends.

#### **Extensions**

* 2a. The user provides an invalid contact index.
    * 2a1. CLinkedin shows an error message that the index is invalid.

      Use case resumes at step 1.

* 2b. The tag does not exist.
    * 2b1. CLinkedin shows an error message that the tag does not exist.

      Use case resumes at step 1.

* 1a. User requests to unassign a tag from one or more contacts.
    * 1a1. CLinkedin validates the provided contact indexes and tag name.
    * 1a2. CLinkedin removes the tag from the specified contacts.
    * 1a3. CLinkedin displays a success message.

      Use case ends.
---
### **Use Case: Find contacts by company**

**Preconditions:**

* The application is running.
* The contact list is not empty.

**Guarantees:**

* If successful, a filtered list of contacts matching the company keywords is displayed.
* If unsuccessful, the contact list remains unchanged.

#### **MSS**

1. User requests to find contacts by entering the `findcom` command with one or more company keywords.
2. CLinkedin parses the input keywords.
3. CLinkedin filters the contact list to include contacts whose company matches any of the keywords.
4. CLinkedin displays the filtered list of contacts along with a summary message.

   Use case ends.

#### **Extensions**

* 1a. The user provides no keywords.

    * 1a1. CLinkedin shows an error message that the command format is invalid.

      Use case resumes at step 1.

* 2a. The input format is invalid.

    * 2a1. CLinkedin shows an error message that the command format is invalid.

      Use case resumes at step 1.

* 3a. No contacts match the given keywords.

    * 3a1. CLinkedin displays an empty list with a summary message indicating 0 contacts found.

      Use case ends.

---

### **Use Case: Sort contacts by company**

**Preconditions:**

* The application is running.
* The contact list is not empty.

**Guarantees:**

* If successful, the currently displayed contact list is sorted by company name.
* If unsuccessful, the contact list remains unchanged.

#### **MSS**

1. User requests to sort contacts by entering the `sortcom` command.
2. CLinkedin sorts the currently displayed contact list alphabetically by company name (case-insensitive).
3. CLinkedin displays the sorted contact list.
4. CLinkedin displays a success message.

   Use case ends.

#### **Extensions**

* 1a. The user provides an invalid command format.

    * 1a1. CLinkedin shows an error message that the command format is invalid.

      Use case resumes at step 1.

---

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java 17 or above installed.
2. The application should respond within 1 second for common commands (add, edit, delete, find, sort) when handling up to 1000 contacts.
3. A user with average typing speed should be able to complete common tasks (e.g., add, edit, find) within 10 seconds using commands.
4. Should automatically save all changes to the local data file after each successful command.
5. The application should handle invalid inputs gracefully without crashing and provide clear error messages.
6. Data should be stored locally in a human-editable format (e.g., JSON).
7. Should not require an internet connection or any remote server to function.
8. Should be packaged into a single JAR file and should not require installation.
9. The application should display correctly on screen resolutions of 1920×1080 and above.
10. The application should not lose data during normal operation.
11. Commands should produce consistent results for the same input.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Contact**: A stored entry representing a person, consisting of fields such as name, phone, email, address, company, remark, and tags.
* **Command**: A text-based instruction entered by the user to perform an action (e.g., add, edit, find).
* **Tag**: A short label used to categorise contacts (e.g., recruiter, fintech).
* **Company**: The organisation or company that a contact is associated with.
* **Remark**: Additional notes or comments about a contact.
* **Filtered contact list**: The subset of contacts currently displayed after applying a command.
* **Duplicate contact**: Two contacts with the same phone number.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Adding a person
1. Adding a person to the contact list

    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/clementi`<br>
       Expected: Contact is added to the end of the list. Details of contact shown in status message. Timestamp added to contact detail.

    1. Test case: `add n/John Doe p/98765432 e/johnd@example.com a/clementi r/met on internship`<br>
      Expected: Contact with optional remark field is added to the end of the list. Details of contact shown in status message. Timestamp added to contact detail.

    1. Test case: `add n/John Doe p/98765432`<br>
      Expected: Contact is not added. Error message given as required fields `EMAIL` and `ADDRESS` are missing.

    1. Other incorrect add commands to try `add`, `add n/john`, `...`<br>
       Expected: Similar to previous

### Editing a person
1. Editing a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `edit 1 n/Johnny`<br>
       Expected: First contact's name is updated to `Johnny`. Details of contact shown in status message.
   
    1. Test case: `edit 0 n/Johnny`<br>
       Expected: No person is edited. Error details shown in the status message.

    1. Other incorrect edit commands to try: `edit`, `edit x`, `...` (where x is large than the list size)<br>
       Expected: Similar to previous.
### Deleting a person

1. Deleting a person while all persons are being shown

    1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Finding contact by company
1. Finding contacts by company

    1. Prerequisites: Multiple contacts in the list with company names.

    1. Test case: `findcom google`<br>
       Expected: Contacts with the company `google` are shown in the list. Success message shown.

    1. Test case: `findcom`<br>
       Expected: No contacts are shown. Error details shown in the status message.

### Sorting contact list by company name
1. Sorting contacts by company name alphabetically

    1. Prerequisites: Multiple contacts in the list with company names.

    1. Test case: `sortcom`<br>
       Expected: Contacts are shown in alphabetical order by company name. Success message shown.

### Viewing recently deleted contacts
1. Viewing deleted contacts

    1. Prerequisites: At least 1 contact has been deleted within the past 7 days.

    1. Test case: `deleted`<br>
       Expected: Deleted contacts within the past 7 days are shown in the list. Success message shown.

### Restoring a deleted contact
1. Restoring a deleted contact from the deleted list

    1. Prerequisites: At least 1 contact has been deleted within the past 7 days.

    1. Test case: `restore 1`<br>
       Expected: First deleted contact is restored to the end of the contact list. Details of restored contact shown in the status message.

### Creating a new tag
1. Creating a tag in the system

    1. Test case: `tag create friends`<br>
       Expected: A new tag `friends` with a default color is added to the system. Success message shown.

    1. Test case: `tag create vip color/gold`<br>
       Expected: A new tag `vip` with gold color is added to the system. Success message shown.

### Assigning tag to contacts
1. Assigning an existing tag to contacts

    1. Prerequisites: Multiple contacts in the list. Tag `friends` exists. No contact currently has the `friends` tag.

    1. Test case: `tag assign 1,2 friends`<br>
       Expected: The `friends` tag is added to the first and second contacts. Success message shown.

### Removing a tag from contacts
1. Removing a tag from contacts

    1. Prerequisites: Multiple contacts in the list. At least 1 contact has the `friends` tag.

    1. Test case: `tag unassign 1 friends`<br>
       Expected: The `friends` tag is removed from the first contact. Success message shown.

### Deleting a tag from the system
1. Deleting an existing tag

    1. Prerequisites: At least 1 contact in the list. Tag `vip` exists. At least 1 contact has the `vip` tag.

    1. Test case: `tag delete vip`<br>
       Expected: The `vip` tag is deleted from the system. Contacts that previously had the `vip` tag no longer have it. Success message shown.

### Viewing all existing tags
1. Viewing all tags in the system

    1. Prerequisites: At least 1 tag exists in the system.

    1. Test case: `tag list`<br>
       Expected: A success message is shown with the list of all existing tags.

### Renaming an existing tag
1. Renaming a tag

    1. Prerequisites: At least 1 contact in the list. Tag `friends` exists. At least 1 contact has the `friends` tag.

    1. Test case: `tag rename friends mates`<br>
       Expected: The `friends` tag is renamed to `mates`. Contacts that previously had the `friends` tag now show `mates` instead. Success message shown.

### Changing the color of an existing tag
1. Changing the color of a tag

    1. Prerequisites: At least 1 tag exists in the system. Tag `friends` exists.

    1. Test case: `tag color friends gold`<br>
       Expected: The color of the `friends` tag is changed to gold. Success message shown.

### Showing contacts with a specific tag
1. Filtering contacts by tag

    1. Prerequisites: At least 1 contact has the tag `friends`. Tag `friends` exists.

    1. Test case: `tag show friends`<br>
       Expected: Contacts with the tag `friends` are shown in the list. Success message shown.

### Saving data
1. Handling missing or corrupted data file

    1. Prerequisites: Navigate to the `data` folder.

    1. Test case: Delete the `addressbook.json` file and launch the application.<br>
       Expected: A new data file is created.

    1. Test case: Modify the `addressbook.json` file so that it becomes corrupted, then launch the application.<br>
       Expected: Application starts with an empty dataset.

## **Appendix: Effort**

This project builds on AddressBook-Level3 (AB3) as a reference point, but introduces additional complexity through enhanced data modeling and features.

### **Overall Difficulty**

Compared to AB3, which manages a single entity type, our project is more complex due to the introduction of a structured tag system and additional state management (e.g., deleted records and restoration).

### **Key Challenges**

- Redesigning the **tag system** from simple strings into **entities** with attributes (e.g., color) and validation rules
- Ensuring **data consistency** across all persons referencing the same tag
- Updating existing logic to support tag creation, deletion, assignment, and renaming
- Implementing **deleted person tracking and restoration**, including synchronising restored data with current tag definitions
- Handling edge cases where tags are renamed or deleted after a person is removed
- Enhancing **input validation**, particularly for email and link fields
- Introducing **clickable links** to improve usability
- Standardising **color input formats** (hex, named colors) and enforcing strict validation to prevent unintended inputs

### **Effort Required**

Effort was mainly spent on:
- Refactoring the model to support tag entities
- Updating commands and parsers for new behaviours
- Handling edge cases in restoration logic
- Strengthening validation and improving user experience

### **Reuse of Existing Code**

We reused AB3’s core architecture (commands, parser, model), which reduced setup effort. However, adapting it to support the new tag system and restoration features required significant modifications.

### **Achievements**

- Transformed tags into fully managed entities
- Implemented deleted-person tracking and restoration
- Improved validation and usability (e.g., clickable links and standardized color input)  

## **Appendix: Planned Enhancements**
These enhancements are planned for future iterations:
- Improve tag restoration behaviour: when restoring a deleted contact, tags that were renamed will be correctly mapped to their updated names instead of being removed.
