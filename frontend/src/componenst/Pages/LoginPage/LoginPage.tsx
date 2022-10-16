import { Formik } from 'formik';
import React, { FC, useState } from 'react';
import { Button, Form, FormControl, FormGroup } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { useAppDispatch } from '../../../hooks/defaultHooks';
import { useLoginMutation } from '../../../store/api/authApiSlice/authApiSlice';
import { login } from '../../../store/slices/authSlice';
import { routes } from '../../../utils/routes';

const LoginPage: FC = () => {
  const [userLogin] = useLoginMutation();
  const [failedLogin, setFailedLogin] = useState<boolean>(false);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const validationSchema = yup.object().shape({
    email: yup.string().email(t('formErrors.invalidEmail')).required(t('formErrors.required')),
    password: yup
      .string()
      .min(8, t('formErrors.minPasswordLength'))
      .max(30, t('formErrors.maxPasswordLength'))
      .required(t('formErrors.required')),
  });

  return (
    <div className='container d-flex justify-content-center align-items-center h-100 gap-5'>
      <Formik
        initialValues={{ email: '', password: '' }}
        validateOnChange={false}
        validationSchema={validationSchema}
        onSubmit={async (values) => {
          const { email, password } = values;
          const emailInLowerCase = email.toLocaleLowerCase();
          try {
            const response = await userLogin({ email: emailInLowerCase, password }).unwrap();
            dispatch(login(response));
            navigate(routes.pages.mainPagePath());
            setFailedLogin(false);
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
          } catch (error: any) {
            const { status } = error;
            if (status === 401) {
              setFailedLogin(!failedLogin);
            }
          }
        }}
      >
        {({ touched, values, handleChange, handleSubmit, isValid, errors, dirty, setErrors }) => {
          const onChangeHandle = (event: React.ChangeEvent) => {
            handleChange(event);
            setErrors({});
            setFailedLogin(false);
          };
          return (
            <Form
              onSubmit={handleSubmit}
              className='col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column'
            >
              <h2 className='mb-4'>{t('login.header')}</h2>
              <FormGroup className='form-floating mb-3'>
                <p>{t('login.email')}:</p>
                <div className='container'>
                  <FormControl
                    type='text'
                    name='email'
                    id='email'
                    placeholder={t('login.emailPlaceholder')}
                    onChange={onChangeHandle}
                    isInvalid={!!errors.email}
                    value={values.email}
                  />
                  {(touched.email && errors.email) || touched.email ? (
                    <FormControl.Feedback type='invalid' tooltip>
                      {errors.email}
                    </FormControl.Feedback>
                  ) : null}
                </div>
              </FormGroup>
              <FormGroup className='form-floating mb-3'>
                <p>{t('login.password')}:</p>
                <div className='container'>
                  <FormControl
                    type='password'
                    className='form-control'
                    name='password'
                    id='password'
                    placeholder={t('login.passwordPlaceholder')}
                    onChange={onChangeHandle}
                    value={values.password}
                    isInvalid={!!errors.password}
                  />
                  {(touched.password && errors.password) || touched.password ? (
                    <FormControl.Feedback type='invalid' tooltip>
                      {errors.password}
                    </FormControl.Feedback>
                  ) : null}
                </div>
              </FormGroup>
              {failedLogin ? <div className='text-danger'>{t('login.failedLogin')}</div> : null}
              <Button
                variant='outline-primary'
                className='w-50 mx-auto mb-3 dblock'
                type='submit'
                disabled={!isValid && !dirty}
              >
                {t('login.enter')}
              </Button>
              <div className='link text-center'>
                {t('login.newUser')}{' '}
                <span>
                  <Link to='/signup'>{t('login.register')}</Link>
                </span>
              </div>
            </Form>
          );
        }}
      </Formik>
    </div>
  );
};

export default LoginPage;
