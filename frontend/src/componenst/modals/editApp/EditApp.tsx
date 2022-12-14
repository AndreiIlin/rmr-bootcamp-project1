import React, { FC } from 'react';
import { Modal } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { toast } from 'react-toastify';
import { useAppDispatch, useAppSelector } from '../../../hooks/defaultHooks';
import { App } from '../../../models/services/app';
import { modalExtra } from '../../../selectors/selectors';
import { useUpdateAppMutation } from '../../../store/api/appsApiSlice/appsApiSlice';
import { closeModal } from '../../../store/slices/modalSlice';
import { isFetchBaseQueryError } from '../../../utils/helpers';
import AppForm from '../../appForm';

const EditApp: FC = () => {
  const { t } = useTranslation();
  const dispatch = useAppDispatch();
  const data = useAppSelector(modalExtra) as unknown as App;
  const [update] = useUpdateAppMutation();
  const onSubmit = async (values: App | Required<App>) => {
    try {
      await update(values).unwrap();
      toast.success(t('toast.appChanged'));
      dispatch(closeModal());
    } catch (error) {
      if (isFetchBaseQueryError(error)) {
        console.log(error);
      }
    }
  };
  return (
    <>
      <Modal.Header className="border-0" closeVariant={'dark'} closeButton />
      <Modal.Body>
        <AppForm
          initialValues={data}
          onSubmit={onSubmit}
          headerText={t('modals.editApp')}
          buttonText={t('modals.change')}
        />
      </Modal.Body>
    </>
  );
};

export default EditApp;
