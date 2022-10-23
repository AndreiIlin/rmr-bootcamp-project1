import React, { FC } from 'react';
import { Modal as ModalComponent } from 'react-bootstrap';
import { useAppDispatch, useAppSelector } from '../../hooks/defaultHooks';
import selectors from '../../selectors';
import { closeModal } from '../../store/slices/modalSlice';
import AppLink from './appLink';
import AppRules from './appRules';
import EditApp from './editApp';
import SuggestBug from './suggestBug';
import SuggestFeature from './suggestFeature';

const mapping = {
  appRules: AppRules,
  appLink: AppLink,
  editApp: EditApp,
  suggestBug: SuggestBug,
  suggestFeature: SuggestFeature,
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
