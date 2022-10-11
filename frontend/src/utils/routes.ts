export const routes = {
  loginPagePath: () => '/login',
  signupPagePath: () => '/signup',
  mainPagePath: () => '/',
  appPagePath: () => '/:id',
  newAppPagePath: () => `/newApp`,
  loginPath: () => '/users/login',
  signupPath: () => '/users/signup',
  newApp: () => '/apps/new',
  allApps: () => '/apps',
  app: (id: string) => `/app/${id}`,
  userInfo: () => 'users/me',
  basePath: () => 'http://localhost:8080',
};
