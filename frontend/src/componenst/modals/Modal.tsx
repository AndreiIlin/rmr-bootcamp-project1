import React, { FC } from 'react';
import { Modal as ModalComponent } from 'react-bootstrap';
import { useAppDispatch, useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import { closeModal } from '../../store/slices/modalSlice';
import AppLink from './appLink';
import AppRules from './appRules';
import EditApp from './editApp';
import AppReports from './appReports';
import UserReplanishment from './userReplanishment';

const mapping = {
  appRules: AppRules,
  appLink: AppLink,
  editApp: EditApp,
  appReports: AppReports,
  addMoney: UserReplanishment,
};

const Modal: FC = () => {
  const dispatch = useAppDispatch();
  const isOpened = useAppSelector(selectors.modalIsOpened);
  const modalType = useAppSelector(selectors.modalType);
  const ModalBody = mapping[modalType as keyof typeof mapping];
  const handleClose = () => {
    dispatch(closeModal());
  };
  return (
    <ModalComponent show={isOpened} onHide={handleClose} centered size="lg">
      {ModalBody && <ModalBody />}
    </ModalComponent>
  );
};

export default Modal;
