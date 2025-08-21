# TestNG Base Project for Web and API Applications

A Java Maven project for web application testing using Selenium WebDriver and TestNG.

## Setup

1. **Clone the repository:**
   ```sh
   git clone https://github.com/satishkovuru/TestNg-Web-API-Testing.git
   cd TestNg-Web-API-Testing
   ```

2. **Install dependencies:**
   ```sh
   mvn clean install
   ```

3. **Configure Selenium Grid (if needed):**
   - Ensure Selenium Grid is running at [http://localhost:4444](http://localhost:4444).

4. **Edit configuration:**
   - Update `src/test/resources/config.json` as needed (e.g., set `"baseUrl"`).

## Usage

- **Run all tests:**
  ```sh
  mvn test
  ```

- **Run with a specific TestNG suite:**
  ```sh
  mvn test -DsuiteXmlFile=src/test/resources/suites/testng.xml
  ```

- **Change browser:**
  - Edit the `browser` parameter in `testng.xml` under `src/test/resources/suites/`.

## Contribution

1. Fork the repository.
2. Create a new branch:
   ```sh
   git checkout -b feature/your-feature
   ```
3. Make your changes.
4. Run tests and ensure code style passes:
   ```sh
   mvn spotless:check test
   ```
5. Commit and push:
   ```sh
   git push origin feature/your-feature
   ```
6. Open a pull request.

## Code Style

- Enforced via Spotless (Google Java Format).
- Run `mvn spotless:apply` to auto-format code.

## CI

- GitHub Actions runs tests and code style checks on every push and pull request.
- See `.github/workflows/ci.yml` for details.

## Project Structure

```
src/main/java/        # Main source code (Page Objects, utilities)
src/test/java/        # Test classes
src/test/resources/   # Test resources (config, testng suites)
```

## Configuration

`src/test/resources/config.json` example:
```json
{
  "baseUrl": "https://www.google.com",
  "anotherUrl": "https://example.com"
}
```

## BrowserStack Integration

This project supports running tests on BrowserStack for cross-browser cloud testing.

### Prerequisites
- [BrowserStack account](https://www.browserstack.com/users/sign_up)
- Your BrowserStack Username and Access Key

### Setup Steps
1. **Set BrowserStack credentials as environment variables:**
   ```sh
   export BROWSERSTACK_USERNAME=your_browserstack_username
   export BROWSERSTACK_ACCESS_KEY=your_browserstack_access_key
   ```
   Or add them as secrets in your CI/CD pipeline.

2. **Configure browserstack.yml (optional):**
   - Edit `browserstack.yml` to specify browsers, OS, and other settings.

3. **Run tests on BrowserStack:**
   ```sh
   mvn test
   ```
   The framework will automatically use BrowserStack if credentials are set.

### Notes
- The framework uses Selenium RemoteWebDriver to connect to BrowserStack.
- You can view your test runs and logs on the [BrowserStack Automate dashboard](https://automate.browserstack.com/).

## License

This project is for educational purposes.