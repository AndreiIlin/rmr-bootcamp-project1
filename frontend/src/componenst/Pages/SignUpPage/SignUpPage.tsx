import { Formik } from 'formik';
import React, { FC } from 'react';
import { Button, Form, FormControl, FormGroup, FormLabel } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import * as yup from 'yup';

const SignUpPage: FC = () => {
  const { t } = useTranslation();
  const validationSchema = yup.object().shape({
    email: yup.string().email().required(),
    password: yup.string().required(),
    confirmPassword: yup
      .string()
      .test(
        'confirmPassword',
        'signUpPage.confirmPassword',
        (password, context) => password === context.parent.password,
      ),
  });
  return (
    <div className="container d-flex justify-content-center align-items-center h-100 gap-5">
      <Formik
        initialValues={{
          email: '',
          password: '',
          confirmPassword: '',
          confirmUserAgreement: false,
          processingOfPersonalData: false,
        }}
        validateOnBlur
        validationSchema={validationSchema}
        onSubmit={(values) => console.log(values)}
      >
        {({ touched, values, handleChange, handleBlur, handleSubmit, isValid, errors, dirty }) => (
          <Form
            onSubmit={handleSubmit}
            className="col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column"
          >
            <h2 className="mb-4">{t('registration.header')}</h2>
            <FormGroup className="form-floating mb-3">
              <p>{t('registration.email')}:</p>
              <div className="container">
                <FormControl
                  type="email"
                  className="form-control"
                  name="email"
                  id="email"
                  placeholder={t('registration.emailPlaceholder')}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  value={values.email}
                />
                {touched && errors.email ? <FormLabel>{errors.email}</FormLabel> : null}
              </div>
            </FormGroup>
            <FormGroup className="form-floating mb-3">
              <p>{t('registration.password')}:</p>
              <div className="container">
                <FormControl
                  type="password"
                  className="form-control"
                  name="password"
                  id="password"
                  placeholder={t('registration.passwordPlaceholder')}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  value={values.password}
                />
                {touched && errors.password ? <FormLabel>{errors.password}</FormLabel> : null}
              </div>
            </FormGroup>
            <FormGroup className="form-floating mb-3">
              <p>{t('registration.confirm')}</p>
              <div className="container">
                <FormControl
                  type="confirmPassword"
                  className="form-control"
                  name="confirmPassword"
                  id="confirmPassword"
                  placeholder={t('registration.confirmPlaceholder')}
                  onChange={handleChange}
                  onBlur={handleBlur}
                  value={values.password}
                />
                {touched && errors.password ? <FormLabel>{errors.password}</FormLabel> : null}
              </div>
            </FormGroup>
            <FormGroup className="mt-3">
              <Form.Check
                type="checkbox"
                id="confirmUserAgreement"
                label={t('registration.rules')}
              />
              <Form.Check
                type="checkbox"
                id="processingOfPersonalData"
                label={t('registration.pd')}
              />
            </FormGroup>
            <Button
              variant="outline-primary"
              className="w-50 mx-auto mb-3 dblock mt-5"
              type="submit"
              disabled={!isValid && !dirty}
            >
              {t('registration.submit')}
            </Button>
            <div className="link text-center">
              {t('registration.notNew')}{' '}
              <span>
                <Link to="/login">{t('registration.enter')}</Link>
              </span>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default SignUpPage;
