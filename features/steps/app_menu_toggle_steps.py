from behave import given, then, when


def toggle_menu(current_state):
    return "open" if current_state == "closed" else "closed"


@given("the app menu is closed")
def step_app_menu_is_closed(context):
    context.app_menu_state = "closed"


@given("the app menu is open")
def step_app_menu_is_open(context):
    context.app_menu_state = "open"


@when("I click the menu")
def step_click_menu(context):
    context.app_menu_state = toggle_menu(context.app_menu_state)


@then("the app menu should be open")
def step_app_menu_should_be_open(context):
    assert context.app_menu_state == "open"


@then("the app menu should be closed")
def step_app_menu_should_be_closed(context):
    assert context.app_menu_state == "closed"
