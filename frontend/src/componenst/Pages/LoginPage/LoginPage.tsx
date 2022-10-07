import { Formik } from 'formik';
import React, { FC } from 'react';
import { Button, Form, FormControl, FormGroup, FormLabel } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import * as yup from 'yup';

const LoginPage: FC = () => {
  const { t } = useTranslation();
  const validationSchema = yup.object().shape({
    email: yup.string().email().required(),
    password: yup.string().required(),
  });
  return (
    <div className="container d-flex justify-content-center align-items-center h-100 gap-5">
      <Formik
        initialValues={{ email: '', password: '' }}
        validateOnBlur
        validationSchema={validationSchema}
        onSubmit={(values) => console.log(values)}
      >
        {({ touched, values, handleChange, handleBlur, handleSubmit, isValid, errors, dirty }) => (
          <Form
            onSubmit={handleSubmit}
            className="col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column"
          >
            <h2 className="mb-4">{t('login.header')}</h2>
            <FormGroup className="form-floating mb-3">
              <p>{t('login.email')}:</p>
              <FormControl
                type="email"
                className="form-control"
                name="email"
                id="email"
                placeholder={t('login.emailPlaceholder')}
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.email}
              />
              {touched && errors.email ? <FormLabel>{errors.email}</FormLabel> : null}
            </FormGroup>
            <FormGroup className="form-floating mb-3">
              <p>{t('login.password')}:</p>
              <FormControl
                type="password"
                className="form-control"
                name="password"
                id="password"
                placeholder={t('login.passwordPlaceholder')}
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.password}
              />
              {touched && errors.password ? <FormLabel>{errors.password}</FormLabel> : null}
            </FormGroup>
            <Button
              variant="outline-primary"
              className="w-50 mx-auto mb-3 dblock"
              type="submit"
              disabled={!isValid && !dirty}
            >
              {t('login.enter')}
            </Button>
            <div className="link text-center">
              {t('login.newUser')}{' '}
              <span>
                <Link to="/signup">{t('login.register')}</Link>
              </span>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default LoginPage;
