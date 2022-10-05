import React, { FC } from 'react';
import * as yup from 'yup';
import { Formik } from 'formik';
import { Form, Button, FormControl, FormLabel, FormGroup } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const SignUpPage: FC = () => {
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
    <div className='container d-flex justify-content-center align-items-center h-100 gap-5'>
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
        onSubmit={(values) => console.log(values)}>
        {({ touched, values, handleChange, handleBlur, handleSubmit, isValid, errors, dirty }) => (
          <Form
            onSubmit={handleSubmit}
            className='col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column'>
            <h2 className='mb-4'>Вход в приложение</h2>
            <FormGroup className='form-floating mb-3'>
              <p>Email:</p>
              <div className='container'>
                <FormControl
                  type='email'
                  className='form-control'
                  name='email'
                  id='email'
                  placeholder='Введите email'
                  onChange={handleChange}
                  onBlur={handleBlur}
                  value={values.email}
                />
                {touched && errors.email ? <FormLabel>{errors.email}</FormLabel> : null}
              </div>
            </FormGroup>
            <FormGroup className='form-floating mb-3'>
              <p>Пароль:</p>
              <div className='container'>
                <FormControl
                  type='password'
                  className='form-control'
                  name='password'
                  id='password'
                  placeholder='Введите пароль'
                  onChange={handleChange}
                  onBlur={handleBlur}
                  value={values.password}
                />
                {touched && errors.password ? <FormLabel>{errors.password}</FormLabel> : null}
              </div>
            </FormGroup>
            <FormGroup className='form-floating mb-3'>
              <p>Подтвержение пароля:</p>
              <div className='container'>
                <FormControl
                  type='confirmPassword'
                  className='form-control'
                  name='confirmPassword'
                  id='confirmPassword'
                  placeholder='Введите пароль'
                  onChange={handleChange}
                  onBlur={handleBlur}
                  value={values.password}
                />
                {touched && errors.password ? <FormLabel>{errors.password}</FormLabel> : null}
              </div>
            </FormGroup>
            <FormGroup className='mt-3'>
              <Form.Check
                type='checkbox'
                id='confirmUserAgreement'
                label='Согласие с условиями портала'
              />
              <Form.Check
                type='checkbox'
                id='processingOfPersonalData'
                label='Согласие на обработку персональных данных'
              />
            </FormGroup>
            <Button
              variant='outline-primary'
              className='w-50 mx-auto mb-3 dblock mt-5'
              type='submit'
              disabled={!isValid && !dirty}>
              Зарегистрироваться
            </Button>
            <div className='link text-center'>
              Есть аккаунт?{' '}
              <span>
                <Link to='/login'>Войти</Link>
              </span>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default SignUpPage;
