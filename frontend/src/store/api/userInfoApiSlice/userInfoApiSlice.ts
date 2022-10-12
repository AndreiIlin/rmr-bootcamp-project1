import { UserInfoResponse } from '../../../models/services/user';
import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

const userInfoApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    getUserInfo: build.query<UserInfoResponse, void>({
      query: () => routes.api.userInfo(),
    }),
  }),
});

export const { useGetUserInfoQuery } = userInfoApiSlice;
