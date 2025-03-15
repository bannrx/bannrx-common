## Repository information

- Name: common
- Description: This is bannrx common library with common functionality across the bannrx micro-services. 

## Setup Steps

- `gradle.properties` file should be added in the repository root folder with the below environment variable
  - `environment` with the value as `local`
- A comment has been added to uncomment the below line in the file `setting.gradle.kts`. This should be followed.
- For logging, an annotation `Loggable` has been created. This should be added to all the service and controllers.