import { configureStore } from '@reduxjs/toolkit';
import { trueStoreApi } from './api/trueStoreApi';
import authReducer from './slices/authSlice';

export const store = configureStore({
  reducer: {
    auth: authReducer,
    [trueStoreApi.reducerPath]: trueStoreApi.reducer,
  },
  middleware: getDefaultMiddleware => getDefaultMiddleware().concat(trueStoreApi.middleware),
  devTools: true,
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
