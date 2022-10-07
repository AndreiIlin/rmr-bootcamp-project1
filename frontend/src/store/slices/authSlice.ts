import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '..';
import { AuthResponse } from '../../models/services/auth';

interface AuthState {
  isAuth: boolean;
}

const initialState: AuthState = {
  isAuth: !!localStorage.getItem('trueStore'),
};

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    login: (state, action: PayloadAction<AuthResponse>) => {
      state.isAuth = true;
      localStorage.setItem('trueStore', JSON.stringify(action.payload));
    },
    logout: (state) => {
      state.isAuth = false;
      localStorage.removeItem('trueStore');
    },
  },
});

export const selectors = (state: RootState) => state.auth;
export const userAuth = (state: RootState) => state.auth.isAuth;

export default authSlice.reducer;
export const { login, logout } = authSlice.actions;
