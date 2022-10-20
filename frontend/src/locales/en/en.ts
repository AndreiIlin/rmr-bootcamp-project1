/* eslint-disable camelcase */
import storeRules from './rules';

export default {
  translation: {
    formErrors: {
      required: 'Fill in the field',
      minPasswordLength: 'Password length must be at least 8 characters',
      maxPasswordLength: 'Password length must not exceed 30 characters',
      invalidEmail: 'Invalid email format',
      invalidPrice: 'Cost must not be less than zero',
      appName: 'Application name must not be less than 3 and more than 30 characters',
      appDescription: 'Application description must not exceed 5000 characters',
      appURL: 'Link must be a valid URL',
    },
    login: {
      header: 'Application Login',
      email: 'Электронная почта',
      emailPlaceholder: 'Enter email...',
      password: 'Password',
      passwordPlaceholder: 'Введите пароль...',
      enter: 'Enter password...',
      newUser: 'Does not have an account?',
      register: 'Register',
      failedEmail: 'No user found with this email address',
      failedPassword: 'Wrong password',
    },
    registration: {
      header: 'Registration',
      email: 'Email',
      emailPlaceholder: 'Enter email...',
      password: 'Password',
      passwordPlaceholder: 'Enter password...',
      confirm: 'Confirm the password',
      confirmPassword: 'Passwords must match',
      confirmPlaceholder: 'Repeat password...',
      rules: 'By clicking the register button you agree to ',
      rulesLink: 'terms of service and rules for the processing of personal data',
      submit: 'Register',
      notNew: 'Have an account?',
      enter: 'Enter',
      alreadyRegistered: 'User with this email is already registered',
      notAgree: 'Alas, you did not agree with the rules of our service and we cannot register you',
      back: 'Return',
    },
    modals: {
      storeRules,
    },
    header: {
      profile: 'Personal area',
      menu: 'Menu',
      apps: 'All applications',
      userApps: 'My apps',
      newApp: 'Add a new application',
      logout: 'Exit',
    },
    page404: {
      header: 'Error 404',
      error:
        'Something seems to have gone wrong! The page you are requesting does not exist. Perhaps it is out of date, has been removed, or an incorrect address has been entered in the address bar.',
      button: 'Go to home page',
    },
    profile: {
      userInfo: 'User information',
      email: 'Email',
      personalBilling: 'Personal wallet',
      bill: 'Check',
      replenishment: 'Top up account',
      passwordChanging: 'Change password',
      oldPassword: 'Old password',
      oldPasswordPlaceholder: 'Enter old password...',
      newPassword: 'New password',
      newPasswordPlaceholder: 'Enter a new password...',
      changePassword: 'Change password',
      differentPassword: 'The new password must be different from the old one',
    },
    appsFields: {
      notFoundUsersApps: 'You have not added any apps yet',
      notFoundApps: 'No apps found in database',
    },
    app: {
      bugPrice: 'Bug cost',
      featurePrice: 'Feature cost',
      testing: 'Sign up for testing',
      description: 'Description',
      comments: 'Comments',
      cost_one: '{{ count }} ruble',
      cost_few: '{{ count }} ruble',
      cost_many: '{{ count }} rubles',
      cost_other: '{{ count }} ruble',
    },
    newAppPage: {
      pageHeader: 'Add a new application',
      enterAppName: 'Enter application name...',
      appName: 'App name:',
      enterAppDescription: 'Enter a description for the app...',
      appDescription: 'Application description:',
      enterFeaturePrice: 'Enter the cost of the feature report...',
      featurePrice: 'Feature report cost:',
      enterBugPrice: 'Enter the cost of the bug report...',
      bugPrice: 'Bug report cost:',
      availableForTesting: 'Available for testing:',
      enterAppImage: 'Enter link to app image...',
      appImage: 'Application image link:',
      enterDownloadUrl: 'Enter the link to download the application:',
      downloadUrl: 'App download link:',
      addButton: 'Add',
    },
    toast: {
      registerSuccess: 'You have successfully registered',
      changePasswordSuccess: 'Password changed',
      addAppSuccess: 'App added',
      addAppError: 'Error adding app',
      networkError: 'Network error',
      serverError: 'Server error, please try again',
    },
    landingPage: {
      header: 'Platform from developers for developers and testers',
      description: 'Thanks to us, your product will become better',
      enterButton: 'Log In',
      firstRole: 'Developers',
      firstDescription:
        'A simple system for downloading, updating the application and receiving feedback from users',
      secondRole: 'QA',
      secondDescription:
        'A simple system of concluding a contract for testing and gaining experience on a real project',
      thirdRole: 'Companies',
      thirdDescription:
        'Reliable platform for hiring employees with experience in development and testing',
    },
  },
};
