import { useFormik } from 'formik';
import React, { FC, useState } from 'react';
import { Button, Card, Container, Form, FormControl } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { useRegistrationMutation } from '../../store/api/authApiSlice/authApiSlice';
import { login } from '../../store/slices/authSlice';
import { openModal } from '../../store/slices/modalSlice';
import { routes } from '../../utils/routes';

const SignUpPage: FC = () => {
  const [alreadyRegistered, setAlreadyRegistered] = useState<boolean>(false);
  const [disabled, setDisabled] = useState(false);
  const [registration] = useRegistrationMutation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const notify = () => toast.success(t('toast.registerSuccess'));
  const validationSchema = yup.object().shape({
    email: yup.string().email(t('formErrors.invalidEmail')).required(t('formErrors.required')),
    password: yup
      .string()
      .min(8, t('formErrors.minPasswordLength'))
      .max(30, t('formErrors.maxPasswordLength'))
      .required(t('formErrors.required')),
    confirmPassword: yup
      .string()
      .test(
        'confirmPassword',
        t('registration.confirmPassword'),
        (password, context) => password === context.parent.password,
      )
      .required(t('formErrors.required')),
  });

  const handleModalClick = () => {
    dispatch(openModal({ type: 'appRules' }));
  };

  const initialValues = {
    email: '',
    password: '',
    confirmPassword: '',
  };

  const formik = useFormik({
    initialValues,
    validationSchema,
    validateOnChange: false,
    onSubmit: async (values) => {
      const { email, password } = values;
      const emailInLowerCase = email.toLocaleLowerCase();
      try {
        setDisabled(true);
        const response = await registration({ email: emailInLowerCase, password }).unwrap();
        dispatch(login(response));
        setAlreadyRegistered(false);
        notify();
        navigate(routes.pages.mainPagePath());
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
      } catch (e: any) {
        if (e?.status === 409) setAlreadyRegistered(true);
      } finally {
        setDisabled(false);
      }
    },
  });

  const onChangeHandle = (event: React.ChangeEvent) => {
    formik.handleChange(event);
    formik.setErrors({});
    setAlreadyRegistered(false);
  };

  return (
    <Container className="my-5 d-flex justify-content-center align-items-center">
      <Form
        onSubmit={formik.handleSubmit}
        className="col-12 col-md-6 mt-3 border p-5 border-primary rounded d-flex flex-column bg-light"
      >
        <h2 className="mb-4">{t('registration.header')}</h2>
        <Form.Group className="position-relative mb-3">
          <Form.Label>{t('registration.email')}:</Form.Label>
          <Form.Control
            type="text"
            name="email"
            id="email"
            placeholder={t('registration.emailPlaceholder')}
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.email}
            value={formik.values.email}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.email}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative mb-3">
          <Form.Label>{t('registration.password')}:</Form.Label>
          <FormControl
            type="password"
            className="form-control"
            name="password"
            id="password"
            placeholder={t('registration.passwordPlaceholder')}
            onChange={onChangeHandle}
            value={formik.values.password}
            isInvalid={!!formik.errors.password}
          />
          <Form.Control.Feedback type="invalid" tooltip>
            {formik.errors.password}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className="position-relative mb-3">
          <Form.Label>{t('registration.confirm')}</Form.Label>
          <FormControl
            type="password"
            className="form-control"
            name="confirmPassword"
            id="confirmPassword"
            placeholder={t('registration.confirmPlaceholder')}
            onChange={onChangeHandle}
            value={formik.values.confirmPassword}
            isInvalid={!!formik.errors.confirmPassword}
          />
          <FormControl.Feedback type="invalid" tooltip>
            {formik.errors.confirmPassword}
          </FormControl.Feedback>
        </Form.Group>
        {alreadyRegistered && (
          <div className="text-danger">{t('registration.alreadyRegistered')}</div>
        )}
        <p>
          {t('registration.rules')}
          <Card.Link href="#" onClick={handleModalClick}>
            {t('registration.rulesLink')}
          </Card.Link>
        </p>
        <Button
          variant="outline-primary"
          className="w-50 mx-auto mb-3"
          type="submit"
          disabled={disabled}
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
    </Container>
  );
};

export default SignUpPage;