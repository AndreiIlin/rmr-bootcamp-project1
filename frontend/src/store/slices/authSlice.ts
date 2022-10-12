import { createSlice, PayloadAction } from '@reduxjs/toolkit';
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

export default authSlice.reducer;
export const { login, logout } = authSlice.actions;
