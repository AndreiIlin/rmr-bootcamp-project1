import { AuthRequest, AuthResponse } from '../../../models/services/auth';
import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

const authApiSlice = trueStoreApi.injectEndpoints({
  endpoints: build => ({
    login: build.mutation<AuthResponse, AuthRequest>({
      query: (userData, role = 'ROLE_USER') => ({
        url: routes.loginPath(),
        method: 'POST',
        body: { ...userData, role },
      }),
    }),
    registration: build.mutation<AuthResponse, AuthRequest>({
      query: (userData, role = 'ROLE_USER') => ({
        url: routes.signupPath(),
        method: 'POST',
        body: { ...userData, role },
      }),
    }),
  }),
});

export const { useLoginMutation, useRegistrationMutation } = authApiSlice;
