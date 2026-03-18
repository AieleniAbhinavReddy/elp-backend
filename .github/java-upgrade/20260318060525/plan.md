# Upgrade Plan: E-Learning-Project (20260318060525)

- **Generated**: 2026-03-18 06:06:30
- **HEAD Branch**: main
- **HEAD Commit ID**: c7942ed

## Available Tools

**JDKs**
- JDK 21.0.8: `C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin` (target JDK for steps 3-4)

**Build Tools**
- Maven Wrapper: `3.9.11` from `.mvn/wrapper/maven-wrapper.properties` (compatible with Java 21)

## Guidelines

- Upgrade Java runtime to LTS Java 21.

## Options

- Working branch: appmod/java-upgrade-20260318060525
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade Java from 17 to 21.

### Technology Stack

| Technology/Dependency | Current | Min Compatible | Why Incompatible |
| --------------------- | ------- | -------------- | ---------------- |
| Java | 17 | 21 | User requested target is Java 21 LTS |
| Spring Boot | 3.3.5 | 3.3.5 | - |
| Maven Wrapper | 3.9.11 | 3.9.0+ | - |
| maven-compiler-plugin | managed by Spring Boot 3.3.5 | 3.11.0+ | - |
| maven-surefire-plugin | managed by Spring Boot 3.3.5 | 3.1.0+ | - |
| MySQL Connector/J | managed by Spring Boot 3.3.5 | managed by Spring Boot 3.3.5 | - |
| jjwt-api | 0.11.5 | 0.11.5 | - |

### Derived Upgrades

- Set `java.version` from `17` to `21` in `pom.xml` (required to compile and run with Java 21).
- Keep Spring Boot at `3.3.5` because it already supports Java 21.
- Keep Maven wrapper at `3.9.11` because it is compatible with Java 21.

## Upgrade Steps

- **Step 1: Setup Environment**
  - **Rationale**: Confirm required Java 21 and Maven wrapper tooling are available before code changes.
  - **Changes to Make**:
    - [ ] Verify Java 21 path and version in local environment
    - [ ] Verify Maven wrapper version and availability
    - [ ] Record tool paths in execution progress
  - **Verification**:
    - Command: `#list_jdks` and wrapper version check
    - Expected: Java 21 and Maven wrapper 3.9.11 available

- **Step 2: Setup Baseline**
  - **Rationale**: Capture baseline compile/test status before upgrading Java target.
  - **Changes to Make**:
    - [ ] Run baseline compilation with current Java target
    - [ ] Run baseline tests and record pass rate
  - **Verification**:
    - Command: `./mvnw.cmd clean test -q`
    - JDK: `C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot`
    - Expected: Baseline compile and tests documented

- **Step 3: Upgrade Project Java Target to 21**
  - **Rationale**: Apply the user-requested runtime and compilation target update in project build configuration.
  - **Changes to Make**:
    - [ ] Update `pom.xml` property `java.version` from `17` to `21`
    - [ ] Re-run compile phase and fix any Java 21 compatibility issues if found
  - **Verification**:
    - Command: `./mvnw.cmd clean test-compile -q`
    - JDK: `C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot`
    - Expected: Main and test code compilation succeeds

- **Step 4: Final Validation**
  - **Rationale**: Validate that all upgrade goals are met with successful compile and full test pass rate.
  - **Changes to Make**:
    - [ ] Verify final Java target value in `pom.xml`
    - [ ] Run full clean test cycle on Java 21
    - [ ] Resolve any remaining compile or test failures until pass
  - **Verification**:
    - Command: `./mvnw.cmd clean test -q`
    - JDK: `C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot`
    - Expected: Compilation succeeds and tests pass 100%

## Key Challenges

- **Toolchain drift between local shell and Maven wrapper**
  - **Challenge**: Build can accidentally run with a non-target JDK.
  - **Strategy**: Set `JAVA_HOME` to Java 21 path before verification commands.
- **Potential dependency behavior changes under Java 21 runtime**
  - **Challenge**: Runtime-only dependencies can reveal issues only during tests.
  - **Strategy**: Run full `clean test` after target change and fix any failures immediately.
