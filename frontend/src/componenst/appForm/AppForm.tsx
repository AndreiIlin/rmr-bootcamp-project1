import { FormikHelpers, useFormik } from 'formik';
import React, { FC } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import * as yup from 'yup';
import { App } from '../../models/services/app';

interface AppFormProps {
  initialValues: App;
  onSubmit: (
    values: App | Required<App>,
    formikHelpers: FormikHelpers<App | Required<App>>,
  ) => void;
  headerText: string;
  buttonText: string;
}

const AppForm: FC<AppFormProps> = ({ initialValues, onSubmit, headerText, buttonText }) => {
  const { t } = useTranslation();
  const validationSchema = yup.object().shape({
    appName: yup
      .string()
      .min(3, t('formErrors.appName'))
      .max(30, t('formErrors.appName'))
      .required(t('formErrors.required')),
    appDescription: yup
      .string()
      .min(1)
      .max(5000, t('formErrors.appDescription'))
      .required(t('formErrors.required')),
    featurePrice: yup
      .number()
      .test('maxDigitsAfterDecimal', t('formErrors.twoDigits'), (number) =>
        /^\d+(\.\d{1,2})?$/.test(number?.toString() ?? ''),
      )
      .min(0, t('formErrors.invalidPrice'))
      .required(t('formErrors.required')),
    bugPrice: yup
      .number()
      .test('maxDigitsAfterDecimal', t('formErrors.twoDigits'), (number) =>
        /^\d+(\.\d{1,2})?$/.test(number?.toString() ?? ''),
      )
      .min(0, t('formErrors.invalidPrice'))
      .required(t('formErrors.required')),
    iconImage: yup.string().url(t('formErrors.appURL')).required(t('formErrors.required')),
    downloadLink: yup.string().url(t('formErrors.appURL')).required(t('formErrors.required')),
  });
  const formik = useFormik({
    initialValues,
    validationSchema: validationSchema,
    validateOnChange: false,
    onSubmit,
  });

  const onChangeHandle = (event: React.ChangeEvent) => {
    formik.handleChange(event);
    formik.setErrors({});
  };

  return (
    <Form
      noValidate
      onSubmit={formik.handleSubmit}
      className="main-bg shadow-lg border border-dark p-5 rounded d-flex flex-column gap-3"
    >
      <h2 className="display-5">{headerText}</h2>
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
          style={{ resize: 'none' }}
          rows={4}
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
          min="0"
          onWheel={(e) => e.currentTarget.blur()}
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
          min="0"
          onWheel={(e) => e.currentTarget.blur()}
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
          placeholder={t('newAppPage.enterDownloadUrl')}
          onChange={onChangeHandle}
          isInvalid={!!formik.errors.downloadLink}
          value={formik.values.downloadLink}
        />
        <Form.Control.Feedback type="invalid" tooltip>
          {formik.errors.downloadLink}
        </Form.Control.Feedback>
      </Form.Group>
      <Button variant="outline-light" type="submit" className="mt-5">
        {buttonText}
      </Button>
    </Form>
  );
};

export default AppForm;
