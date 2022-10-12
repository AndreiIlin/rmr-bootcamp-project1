import { RootState } from '../store';

export const userAuth = (state: RootState) => state.auth.isAuth;
export const modalIsOpened = (state: RootState) => state.modal.isOpened;
export const modalType = (state: RootState) => state.modal.type;

