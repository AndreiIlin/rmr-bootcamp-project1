/* eslint-disable no-undef */
const $HOST = 'http://localhost:8080';

export const routes = {
  pages: {
    loginPagePath: () => '/login',
    signupPagePath: () => '/signup',
    mainPagePath: () => '/applications',
    appsPagePath: () => '/:id',
    appPagePath: (id: string) => `/${id}`,
    newAppPagePath: () => '/newApp',
    userAppsPagePath: () => '/myApps',
    profilePagePath: () => '/profile',
    landingPage: () => '/',
  },
  api: {
    loginPath: () => '/users/login',
    signupPath: () => '/users/signup',
    apps: () => '/apps',
    app: (id: string) => `/apps/${id}`,
    userApps: () => '/apps/my',
    userInfo: () => '/users/me',
    contracts: () => '/contracts',
    contract: (id: string) => `/contracts/${id}`,
    myContract: () => '/contracts/my',
    reports: (param: string) => `/reports/${param}`,
    reportsByApp: (appId: string) => `/reports/app/${appId}`,
    reportsByContractId: (contractId: string) => `/reports/contract/${contractId}/my`,
    userReports: () => '/reports/my',
    withdrawalMoney: () => '/money/withdrawal',
    replenishmentMoney: () => '/money/replenishment',
    userTransitions: () => '/money/my',
    userBalance: () => '/money/balance',
    basePath: () => process.env.BACKEND_HOST ?? $HOST,
  },
};
