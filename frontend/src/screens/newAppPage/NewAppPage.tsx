import React from 'react';
import { Container } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import AppForm from '../../componenst/appForm';
import { App } from '../../models/services/app';
import { useAddNewAppMutation } from '../../store/api/appsApiSlice/appsApiSlice';
import { isFetchBaseQueryError } from '../../utils/helpers';
import { routes } from '../../utils/routes';

const initialValues: App = {
  appName: '',
  appDescription: '',
  featurePrice: 0,
  bugPrice: 0,
  available: true,
  iconImage: '',
  downloadLink: '',
};

const NewAppPage = () => {
  const { t } = useTranslation();
  const [addApp] = useAddNewAppMutation();
  const navigate = useNavigate();
  const onSubmit = async (values: App) => {
    try {
      await addApp(values).unwrap();
      navigate(routes.pages.userAppsPagePath());
      toast.success(t('toast.addAppSuccess'));
    } catch (error) {
      if (isFetchBaseQueryError(error)) {
        const { status } = error;
        if (status === 500) {
          toast.error(t('toast.appExist'));
        } else {
          toast.error(t('toast.serverError'));
        }
      }
    }
  };

  return (
    <Container as="main" className="my-5 d-flex justify-content-center align-items-center" fluid>
      <AppForm
        initialValues={initialValues}
        onSubmit={onSubmit}
        headerText={t('newAppPage.pageHeader')}
        buttonText={t('newAppPage.addButton')}
      />
    </Container>
  );
};

export default NewAppPage;
