import { Formik } from 'formik';
import React from 'react';
import { toast } from 'react-toastify';
import { Button, Form, FormControl, FormGroup } from 'react-bootstrap';
import FormCheckLabel from 'react-bootstrap/esm/FormCheckLabel';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { AddApp } from '../../../models/services/app';
import { useAddNewAppMutation } from '../../../store/api/appsApiSlice/appsApiSlice';
import { routes } from '../../../utils/routes';

const initialValues: AddApp = {
  appName: '',
  appDescription: '',
  featurePrice: '',
  bugPrice: '',
  available: true,
  iconImage: '',
  downloadLink: '',
};

const validationSchema = yup.object().shape({
  appName: yup.string().min(3).max(30).required(),
  appDescription: yup.string().min(1).max(5000).required(),
  featurePrice: yup.number().required(),
  bugPrice: yup.number().required(),
  iconImage: yup.string().url().required(),
  downloadLink: yup.string().url().required(),
});

const NewAppPage = () => {
  const { t } = useTranslation();
  const [addApp] = useAddNewAppMutation();
  const navigate = useNavigate();
  const notify = () => toast.success(t('toast.addAppSuccess'));
  return (
    <main className='app'>
      <div className='container d-flex justify-content-center align-items-center h-100'>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={async (values) => {
            const {
              appName,
              appDescription,
              available,
              featurePrice,
              bugPrice,
              iconImage,
              downloadLink,
            } = values;
            const newApp = {
              appName,
              appDescription,
              featurePrice: Number(featurePrice),
              bugPrice: Number(bugPrice),
              available,
              iconImage,
              downloadLink,
              contractId: null,
            };
            try {
              await addApp(newApp);
              navigate(routes.pages.userAppsPagePath());
              notify();
            } catch (e) {
              console.log(e);
            }
          }}
        >
          {({ errors, handleChange, handleSubmit, values }) => (
            <Form
              onSubmit={handleSubmit}
              className='col-xs-12 col-md-6 mt-3 mt-mb-0 border p-5 border-secondary rounded d-flex flex-column w-100 gap-3'
            >
              <h1>{t('newAppPage.pageHeader')}</h1>
              <FormGroup>
                <p>{t('newAppPage.enterAppName')}</p>
                <FormControl
                  type='text'
                  name='appName'
                  id='appName'
                  onChange={handleChange}
                  value={values.appName}
                />
              </FormGroup>
              <FormGroup>
                <p>{t('newAppPage.enterAppDescription')}</p>
                <FormControl
                  as='textarea'
                  name='appDescription'
                  id='appDescription'
                  onChange={handleChange}
                  isInvalid={!!errors.appDescription}
                  value={values.appDescription}
                />
              </FormGroup>
              <FormGroup>
                <p>{t('newAppPage.enterFeaturePrice')}</p>
                <FormControl
                  type='text'
                  name='featurePrice'
                  id='featurePrice'
                  onChange={handleChange}
                  isInvalid={!!errors.featurePrice}
                  value={values.featurePrice}
                />
              </FormGroup>
              <FormGroup>
                <p>{t('newAppPage.enterBugPrice')}</p>
                <FormControl
                  type='text'
                  name='bugPrice'
                  id='bugPrice'
                  onChange={handleChange}
                  isInvalid={!!errors.bugPrice}
                  value={values.bugPrice}
                />
              </FormGroup>
              <FormGroup className='d-flex gap-3 aligns-items-center'>
                <FormCheckLabel htmlFor='available'>
                  {t('newAppPage.availableForTesting')}
                </FormCheckLabel>
                <Form.Check
                  type='checkbox'
                  id='available'
                  name='available'
                  checked={values.available}
                  onChange={handleChange}
                />
              </FormGroup>
              <FormGroup>
                <p>{t('newAppPage.enterAppImage')}</p>
                <FormControl
                  type='text'
                  name='iconImage'
                  id='iconImage'
                  onChange={handleChange}
                  isInvalid={!!errors.iconImage}
                  value={values.iconImage}
                />
              </FormGroup>
              <FormGroup>
                <p>{t('newAppPage.enterDownloadUrl')}</p>
                <FormControl
                  type='text'
                  name='downloadLink'
                  id='downloadLink'
                  onChange={handleChange}
                  isInvalid={!!errors.downloadLink}
                  value={values.downloadLink}
                />
              </FormGroup>
              <Button type='submit'>{t('newAppPage.addButton')}</Button>
            </Form>
          )}
        </Formik>
      </div>
    </main>
  );
};

export default NewAppPage;
