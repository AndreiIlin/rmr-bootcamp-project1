import { UserInfoResponse } from '../../../models/services/user';
import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

const userInfoApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    userInfo: build.query<UserInfoResponse, void>({
      query: () => routes.userInfo(),
    }),
  }),
});

export const { useUserInfoQuery } = userInfoApiSlice;
