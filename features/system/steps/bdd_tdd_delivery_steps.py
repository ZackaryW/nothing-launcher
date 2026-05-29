from pathlib import Path

from behave import given, then


REPO_ROOT = Path(__file__).resolve().parents[3]


@given("the GitHub Actions build workflow")
def step_build_workflow(context):
    context.workflow_text = (REPO_ROOT / ".github" / "workflows" / "build.yml").read_text()


@then("it should install the behave BDD runner")
def step_installs_behave(context):
    assert "python -m pip install behave" in context.workflow_text


@then("it should run the functional behave suite")
def step_runs_functional_behave(context):
    assert "python -m behave features/functional" in context.workflow_text


@then("it should run the system behave suite")
def step_runs_system_behave(context):
    assert "python -m behave features/system" in context.workflow_text


@then("it should run the debug unit tests")
def step_runs_debug_unit_tests(context):
    assert "./gradlew testDebugUnitTest" in context.workflow_text


@then("it should build the debug APK after the debug unit tests")
def step_builds_debug_after_unit_tests(context):
    unit_test_index = context.workflow_text.find("./gradlew testDebugUnitTest")
    debug_build_index = context.workflow_text.find("./gradlew assembleDebug")
    assert unit_test_index != -1
    assert debug_build_index != -1
    assert unit_test_index < debug_build_index


@given("the project README")
def step_project_readme(context):
    context.readme_text = (REPO_ROOT / "README.md").read_text()


@then("it should document the functional behave suite command")
def step_documents_functional_behave(context):
    assert "python -m behave features/functional" in context.readme_text


@then("it should document the system behave suite command")
def step_documents_system_behave(context):
    assert "python -m behave features/system" in context.readme_text


@then("it should document the debug unit test command")
def step_documents_unit_tests(context):
    assert "./gradlew testDebugUnitTest" in context.readme_text


@then("it should document the debug APK build command")
def step_documents_debug_build(context):
    assert "./gradlew assembleDebug" in context.readme_text
