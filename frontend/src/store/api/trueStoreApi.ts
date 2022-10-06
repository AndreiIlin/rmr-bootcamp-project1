import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { routes } from '../../utils/routes';

export const trueStoreApi = createApi({
  reducerPath: 'trueStoreApi',
  baseQuery: fetchBaseQuery({
    baseUrl: routes.basePath(),
    prepareHeaders: (headers, { getState }) => {
      const token = (JSON.parse(localStorage.getItem('trueStore') as string)).access_token;
      if (token) {
        headers.set('authorization', `Bearer ${token}`);
      }
      return headers;
    },
  }),
  endpoints: builder => ({}),
});
