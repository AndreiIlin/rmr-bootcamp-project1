import { App } from '../../../models/services/app';
import { routes } from '../../../utils/routes';
import { trueStoreApi } from '../trueStoreApi';

const appsApiSlice = trueStoreApi.injectEndpoints({
  endpoints: (build) => ({
    allApps: build.query<App[], void>({
      query: () => routes.mainPagePath(),
      providesTags: ['App'],
    }),
    app: build.query<App, string>({
      query: (id) => routes.app(id),
      providesTags: ['App'],
    }),
    getAllMyApp: build.query<App[], void>({
      query: () => routes.mainPagePath(),
      providesTags: ['App'],
    }),
    addApp: build.mutation<App, Omit<App, 'ownerId' | 'id'>>({
      query: (app) => ({
        url: routes.newApp(),
        method: 'POST',
        body: { ...app },
      }),
      invalidatesTags: ['App'],
    }),
    updateApp: build.mutation<App, App>({
      query: (app) => ({
        url: routes.app(app.id),
        method: 'PATCH',
        body: { ...app },
      }),
      invalidatesTags: ['App'],
    }),
    deleteApp: build.mutation<App, App>({
      query: (app) => ({
        url: routes.app(app.id),
        method: 'DELETE',
      }),
      invalidatesTags: ['App'],
    }),
  }),
});

export const { useAddAppMutation, useUpdateAppMutation, useDeleteAppMutation } = appsApiSlice;
