import { useFormik } from 'formik';
import React from 'react';
import { toast } from 'react-toastify';
import { Button, Container, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { AddApp } from '../../models/services/app';
import { useAddNewAppMutation } from '../../store/api/appsApiSlice/appsApiSlice';
import { routes } from '../../utils/routes';

const initialValues: AddApp = {
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
  const notify = () => toast.success(t('toast.addAppSuccess'));
  const validationSchema = yup.object().shape({
    appName: yup
      .string()
      .min(3, t('formErrors.appName'))
      .max(30, t('formErrors.appName'))
      .required(t('formErrors.required')),
    appDescription: yup
      .string()
      .min(1)
      .max(5000, t('formErrors.appDEscription'))
      .required(t('formErrors.required')),
    featurePrice: yup
      .number()
      .min(0, t('formErrors.invalidPrice'))
      .required(t('formErrors.required')),
    bugPrice: yup.number().min(0, t('formErrors.invalidPrice')).required(t('formErrors.required')),
    iconImage: yup.string().url(t('formErrors.appURL')).required(t('formErrors.required')),
    downloadLink: yup.string().url(t('formErrors.appURL')).required(t('formErrors.required')),
  });
  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    validateOnChange: false,
    onSubmit: async (values) => {
      try {
        await addApp(values);
        navigate(routes.pages.userAppsPagePath());
        notify();
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
      } catch (error: any) {
        const { status } = error;
        if (status === 500) {
          toast.warning(t('toast.serverError'));
        } else {
          toast.warning(t('toast.addAppError'));
        }
      }
    },
  });

  const onChangeHandle = (event: React.ChangeEvent) => {
    formik.handleChange(event);
    formik.setErrors({});
  };

  return (
    <Container as="main" className="my-5 d-flex justify-content-center align-items-center" fluid>
      <Form
        onSubmit={formik.handleSubmit}
        className="col-xs-12 col-md-6 mt-3 mt-mb-0 w-75 border border-primary p-5 rounded d-flex flex-column gap-3 bg-light"
      >
        <h1>{t('newAppPage.pageHeader')}</h1>
        <Form.Group className="position-relative">
          <Form.Label>{t('newAppPage.appName')}</Form.Label>
          <Form.Control
            type="text"
            name="appName"
            id="appName"
            placeholder={t('newAppPage.enterAppName')}
            isInvalid={!!formik.errors.appName}
            onChange={onChangeHandle}
            value={formik.values.appName}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.appName}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative">
          <Form.Label>{t('newAppPage.appDescription')}</Form.Label>
          <Form.Control
            as="textarea"
            name="appDescription"
            placeholder={t('newAppPage.enterAppDescription')}
            id="appDescription"
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.appDescription}
            value={formik.values.appDescription}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.appDescription}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative">
          <Form.Label>{t('newAppPage.featurePrice')}</Form.Label>
          <Form.Control
            type="number"
            name="featurePrice"
            id="featurePrice"
            placeholder={t('newAppPage.enterFeaturePrice')}
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.featurePrice}
            value={formik.values.featurePrice}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.featurePrice}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative">
          <Form.Label>{t('newAppPage.bugPrice')}</Form.Label>
          <Form.Control
            type="number"
            name="bugPrice"
            placeholder={t('newAppPage.enterBugPrice')}
            id="bugPrice"
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.bugPrice}
            value={formik.values.bugPrice}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.bugPrice}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="d-flex gap-3 aligns-items-center">
          <Form.Label htmlFor="available">{t('newAppPage.availableForTesting')}</Form.Label>
          <Form.Check
            type="checkbox"
            id="available"
            name="available"
            checked={formik.values.available}
            onChange={formik.handleChange}
          />
        </Form.Group>
        <Form.Group className="position-relative">
          <Form.Label>{t('newAppPage.appImage')}</Form.Label>
          <Form.Control
            type="text"
            name="iconImage"
            id="iconImage"
            placeholder={t('newAppPage.enterAppImage')}
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.iconImage}
            value={formik.values.iconImage}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.iconImage}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative">
          <Form.Label>{t('newAppPage.downloadUrl')}</Form.Label>
          <Form.Control
            type="text"
            name="downloadLink"
            id="downloadLink"
            placeholder={t('newAppPage.enterAppImage')}
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.downloadLink}
            value={formik.values.downloadLink}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.downloadLink}
          </Form.Control.Feedback>
        </Form.Group>
        <Button type="submit" className="mt-5">
          {t('newAppPage.addButton')}
        </Button>
      </Form>
    </Container>
  );
};

export default NewAppPage;
