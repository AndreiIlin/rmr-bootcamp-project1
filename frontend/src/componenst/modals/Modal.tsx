import React, { FC } from 'react';
import { Modal as ModalComponent } from 'react-bootstrap';
import { useAppDispatch, useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import { closeModal } from '../../store/slices/modalSlice';
import AppRules from './appRules';

const mapping = {
  appRules: AppRules,
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
    <ModalComponent show={isOpened} onHide={handleClose} centered>
      {ModalBody && <ModalBody />}
    </ModalComponent>
  );
};

export default Modal;
