import { UserInfoResponse } from '../../../models/services/user';
import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

interface PasswordRequest {
  oldPassword: string;
  newPassword: string;
}

const userInfoApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    getUserInfo: build.query<UserInfoResponse, void>({
      query: () => routes.api.userInfo(),
    }),
    changePassword: build.mutation<PasswordRequest, PasswordRequest>({
      query: (userData) => ({
        url: routes.api.userInfo(),
        method: 'PATCH',
        body: userData,
      }),
    })
  }),
});

export const { useGetUserInfoQuery, useChangePasswordMutation } = userInfoApiSlice;
