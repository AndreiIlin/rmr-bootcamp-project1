import { App } from '../../../models/services/app';
import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

interface PaginationRequest {
  page: number;
  size: number;
  filter: string;
}

const appsApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    getApps: build.query<App[], PaginationRequest>({
      query: ({ page, size, filter }) => ({
        url: routes.api.apps(),
        params: {
          page,
          size,
          filter,
        },
      }),
      providesTags: ['App'],
    }),
    getApp: build.query<App, string>({
      query: (id) => routes.api.app(id),
      providesTags: ['App'],
    }),
    getUserApps: build.query<App[], PaginationRequest>({
      query: ({ page, size, filter }) => ({
        url: routes.api.userApps(),
        params: {
          page,
          size,
          filter,
        },
      }),
      providesTags: ['App'],
    }),
    addNewApp: build.mutation<App, Omit<App, 'ownerId' | 'id'>>({
      query: (app) => ({
        url: routes.api.apps(),
        method: 'POST',
        body: app,
      }),
      invalidatesTags: ['App'],
    }),
    updateApp: build.mutation<App, App>({
      query: (app) => ({
        url: routes.api.app(app.id),
        method: 'PATCH',
        body: app,
      }),
      invalidatesTags: ['App'],
    }),
    deleteApp: build.mutation<App, string>({
      query: (id) => ({
        url: routes.api.app(id),
        method: 'DELETE',
      }),
      invalidatesTags: ['App'],
    }),
  }),
});

export const {
  useAddNewAppMutation,
  useDeleteAppMutation,
  useUpdateAppMutation,
  useGetAppQuery,
  useGetAppsQuery,
  useGetUserAppsQuery,
} = appsApiSlice;
