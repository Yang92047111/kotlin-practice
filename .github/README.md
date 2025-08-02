# GitHub Actions CI/CD Pipeline

This directory contains a comprehensive GitHub Actions CI/CD pipeline for the Kotlin Practice Projects. The pipeline is designed to ensure code quality, run tests, handle releases, and maintain the project automatically.

## 🚀 Workflow Overview

### 1. **Main CI Pipeline** (`ci.yml`)
**Triggers**: Push to main/master/develop, Pull Requests, Manual dispatch

**Jobs**:
- **Build and Test**: Builds all modules and runs comprehensive test suite
- **Individual Module Tests**: Parallel testing of each module
- **Code Quality**: Security scans and quality checks
- **Integration Tests**: Docker-based integration testing
- **Demo Execution**: Validates that demos run correctly
- **Dependency Check**: Vulnerability scanning
- **Health Check**: Overall project health validation
- **Release Preparation**: Prepares artifacts for release (main branch only)

**Key Features**:
- ✅ Multi-module Maven build
- ✅ Parallel test execution
- ✅ Docker services for integration tests
- ✅ Comprehensive artifact collection
- ✅ Test report generation
- ✅ Security vulnerability scanning

### 2. **Pull Request Check** (`pr-check.yml`)
**Triggers**: PR opened, synchronized, reopened

**Purpose**: Fast validation of pull requests with helpful feedback

**Features**:
- ✅ Quick build validation
- ✅ Fast test execution
- ✅ Automated PR comments with guidance
- ✅ Breaking change detection

### 3. **Nightly Build** (`nightly.yml`)
**Triggers**: Daily at 2 AM UTC, Manual dispatch

**Purpose**: Comprehensive testing and maintenance

**Features**:
- ✅ Extended integration tests
- ✅ Performance testing
- ✅ Dependency vulnerability scans
- ✅ Automated issue creation on failures
- ✅ Dependency update notifications

### 4. **Release Pipeline** (`release.yml`)
**Triggers**: Git tags (v*), Manual dispatch

**Purpose**: Automated release creation and distribution

**Features**:
- ✅ Release validation
- ✅ Artifact building and packaging
- ✅ Automated changelog generation
- ✅ GitHub release creation
- ✅ Post-release task tracking

### 5. **Maintenance** (`maintenance.yml`)
**Triggers**: Weekly on Sundays, Manual dispatch

**Purpose**: Automated project maintenance

**Features**:
- ✅ Dependency update checking
- ✅ Security auditing
- ✅ Code quality analysis
- ✅ Artifact cleanup
- ✅ Automated issue creation for updates

## 📊 Workflow Status Badges

Add these badges to your main README.md:

```markdown
[![CI Pipeline](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/ci.yml/badge.svg)](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/ci.yml)
[![Nightly Build](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/nightly.yml/badge.svg)](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/nightly.yml)
[![Release](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/release.yml/badge.svg)](https://github.com/YOUR_USERNAME/kotlin-practice/actions/workflows/release.yml)
```

## 🔧 Configuration

### Environment Variables
- `JAVA_VERSION`: Java version to use (default: '17')
- `MAVEN_OPTS`: Maven JVM options (default: '-Xmx1024m')

### Secrets Required
No secrets are required for basic functionality. Optional secrets:
- `SONAR_TOKEN`: For SonarCloud integration (commented out)

### Services
- **MySQL**: Automatically started for Testcontainers integration tests
- **Docker**: Available for all jobs requiring containerization

## 📋 Workflow Details

### Build Strategy
1. **Caching**: Maven dependencies are cached for faster builds
2. **Parallel Execution**: Module tests run in parallel using matrix strategy
3. **Artifact Management**: Test reports and build artifacts are preserved
4. **Failure Handling**: Continues on error where appropriate, with proper reporting

### Test Strategy
1. **Unit Tests**: Fast, isolated tests for each module
2. **Integration Tests**: Real database and service integration
3. **Performance Tests**: Basic performance validation (nightly)
4. **Security Tests**: Dependency vulnerability scanning

### Quality Gates
- ✅ All tests must pass
- ✅ Build must succeed
- ✅ Security scans must not find high-severity issues
- ✅ Code quality checks must pass

## 🚀 Usage Guide

### For Developers

#### Running Locally
```bash
# Simulate CI pipeline locally
make ci

# Run specific test suites
make test-coroutines
make test-oracle
make test-testcontainers

# Check project health
make health
```

#### Pull Request Workflow
1. Create feature branch
2. Make changes
3. Push to GitHub
4. PR check workflow runs automatically
5. Address any issues found
6. Merge after approval

#### Release Process
1. Ensure all tests pass on main branch
2. Create and push a version tag:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
3. Release workflow runs automatically
4. GitHub release is created with artifacts

### For Maintainers

#### Manual Workflow Triggers
All workflows support manual triggering via GitHub Actions UI:
- **CI Pipeline**: Test specific branches
- **Nightly Build**: Run comprehensive tests on demand
- **Release**: Create releases without tags
- **Maintenance**: Run maintenance tasks on demand

#### Monitoring
- Check workflow status in GitHub Actions tab
- Review automated issues for maintenance tasks
- Monitor artifact storage usage
- Review security scan results

## 🔍 Troubleshooting

### Common Issues

#### Build Failures
1. Check Java/Maven versions
2. Verify dependency availability
3. Review test logs in artifacts
4. Check for environment-specific issues

#### Test Failures
1. Review test reports in artifacts
2. Check for Docker availability (integration tests)
3. Verify database connectivity
4. Check for timing issues in tests

#### Artifact Issues
1. Verify artifact retention settings
2. Check storage quota
3. Review cleanup policies

### Debug Mode
Enable debug logging by adding to workflow:
```yaml
env:
  ACTIONS_STEP_DEBUG: true
  ACTIONS_RUNNER_DEBUG: true
```

## 📈 Metrics and Reporting

### Available Reports
- **Test Results**: Surefire reports for all modules
- **Security Scans**: OWASP dependency check reports
- **Code Quality**: Basic quality metrics
- **Dependency Updates**: Available updates report

### Artifact Retention
- **Test Results**: 30 days
- **Build Artifacts**: 7 days (regular), 90 days (releases)
- **Security Reports**: 30 days
- **Maintenance Reports**: 30 days

## 🔄 Maintenance

### Automated Tasks
- **Daily**: Nightly comprehensive testing
- **Weekly**: Dependency updates, security audits, cleanup
- **On Release**: Artifact creation, changelog generation
- **On PR**: Quick validation and feedback

### Manual Tasks
- Review and merge dependency update PRs
- Address security vulnerabilities
- Update workflow configurations as needed
- Monitor and optimize build performance

## 🎯 Best Practices

### For Contributors
1. Run `make test` locally before pushing
2. Keep PRs focused and small
3. Write tests for new functionality
4. Update documentation as needed

### For Maintainers
1. Review automated issues regularly
2. Keep dependencies updated
3. Monitor security scan results
4. Optimize workflow performance
5. Update retention policies as needed

## 📚 Additional Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Maven GitHub Actions](https://github.com/actions/setup-java)
- [Docker in GitHub Actions](https://docs.github.com/en/actions/using-containerized-services)
- [Testcontainers CI](https://www.testcontainers.org/supported_docker_environments/continuous_integration/)

---

This CI/CD pipeline is designed to grow with your project. Feel free to customize workflows based on your specific needs and requirements.