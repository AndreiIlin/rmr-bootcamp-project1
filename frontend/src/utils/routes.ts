const $HOST = 'http://localhost:8080';

export const routes = {
  pages: {
    loginPagePath: () => '/login',
    signupPagePath: () => '/signup',
    mainPagePath: () => '/',
    appsPagePath: () => '/:id',
    appPagePath: (id: string) => `/${id}`,
    newAppPagePath: () => '/newApp',
    userAppsPagePath: () => '/myApps',
    profilePagePath: () => '/profile',
  },
  api: {
    loginPath: () => '/users/login',
    signupPath: () => '/users/signup',
    apps: () => '/apps',
    app: (id: string) => `/apps/${id}`,
    userApps: () => '/apps/my',
    userInfo: () => '/users/me',
    // eslint-disable-next-line no-undef
    basePath: () => process.env.BACKEND_HOST ?? $HOST,
  },
};
