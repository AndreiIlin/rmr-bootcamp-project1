/* eslint-disable camelcase */
import storeRules from './rules';

export default {
  translation: {
    formErrors: {
      required: 'Заполните поле',
      minPasswordLength: 'Длина пароля не должна быть меньше 8 символов',
      maxPasswordLength: 'Длина пароля не должна превышать 30 символов',
      invalidEmail: 'Неверный формат email',
      invalidPrice: 'Стоимость не должна быть меньше нуля',
      appName: 'Название приложения не должно быть меньше 3 и больше 30 символов',
      reportTitle: 'Название проблемы не должно быть меньше 3 и больше 255 символов',
      reportDescription: 'Описание проблемы не должно быть меньше 3 и больше 5000 символов',
      appDescription: 'Описание приложения не должно превышать 5000 символов',
      appURL: 'Ссылка должна быть валидным URL',
      twoDigits: 'Неверный денежный формат',
      incorectSymbol: 'Пароль должен состоять только из цифр и букв',
    },
    login: {
      header: 'Вход в приложение',
      email: 'Электронная почта',
      emailPlaceholder: 'Введите электронную почту...',
      password: 'Пароль',
      passwordPlaceholder: 'Введите пароль...',
      enter: 'Войти',
      newUser: 'Нет аккаунта?',
      register: 'Зарегистрироваться',
      failedLogin: 'Неправильный email или пароль',
      failedEmail: 'Пользователь с таким адресом электронной почты не найден',
      failedPassword: 'Неправильно набран пароль',
    },
    registration: {
      header: 'Регистрация',
      email: 'Электронная почта',
      emailPlaceholder: 'Введите электронную почту...',
      password: 'Пароль',
      passwordPlaceholder: 'Введите пароль...',
      confirm: 'Подтвердите пароль',
      confirmPassword: 'Пароли должны совпадать',
      confirmPlaceholder: 'Повторите пароль...',
      rules: 'Нажимая кнопку зарегистрироваться вы соглашаетесь с ',
      rulesLink: 'условиями сервиса и правилами по обработке персональных данных',
      submit: 'Зарегистрироваться',
      notNew: 'Есть аккаунт?',
      enter: 'Войти',
      alreadyRegistered: 'Пользователь с таким email уже зарегистрирован',
      notAgree:
        'Увы но вы не согласились с правилами нашего сервиса и мы не можем вас зарегистрировать',
      back: 'Вернуться',
    },
    modals: {
      storeRules,
      link: 'Ссылка на приложение',
      change: 'Сохранить изменения',
      edit: 'Редактировать приложение',
      featureTitle: 'Предложить фичу',
      bugTitle: 'Сообщить о баге',
      reportTitle: 'Введите описание...',
      reportDescription: 'Введите описание...',
      claimTitle: 'Введите описание жалобы...',
      close: 'Закрыть',
      send: 'Отправить',
    },
    header: {
      profile: 'Личный кабинет',
      menu: 'Меню',
      apps: 'Все приложения',
      userApps: 'Мои приложения',
      newApp: 'Добавить новое приложение',
      logout: 'Выйти',
    },
    page404: {
      header: 'Ошибка 404',
      error:
        'Кажется что-то пошло не так! Страница, которую вы запрашиваете, не существует. Возможно она устарела, была удалена, или был введен неверный адрес в адресной строке.',
      button: 'Перейти на главную страницу',
    },
    profile: {
      userInfo: 'Информация о пользователе',
      email: 'Электронная почта',
      personalBilling: 'Личный кошелёк',
      bill: 'Счёт',
      replenishment: 'Пополнить счёт',
      passwordChanging: 'Изменение пароля',
      oldPassword: 'Старый пароль',
      oldPasswordPlaceholder: 'Введите старый пароль...',
      newPassword: 'Новый пароль',
      newPasswordPlaceholder: 'Введите новый пароль...',
      changePassword: 'Изменить пароль',
      differentPassword: 'Новый пароль должен отличаться от старого',
    },
    appsFields: {
      notFoundUsersApps: 'Вы не добавили еще ни одного приложения',
      notFoundApps: 'Не найдено приложений в базе',
    },
    app: {
      bugPrice: 'Стоимость баг-репорта',
      featurePrice: 'Cтоимость фич-репорта',
      testing: 'Записаться на тестирование',
      download: 'Скачать приложение',
      description: 'Описание',
      comments: 'Комментарии',
      editing: 'Редактировать',
      reports: 'Отчеты',
      suggestFeature: 'Предложить фичу',
      suggestBug: 'Отправить баг-репорт',
      claim: 'Пожаловаться',
      findApp: 'Найти приложение...',
      cost_one: '{{ count }} рубль',
      cost_few: '{{ count }} рубля',
      cost_many: '{{ count }} рублей',
      cost_other: '{{ count }} рублей',
    },
    newAppPage: {
      pageHeader: 'Добавить новое приложение',
      enterAppName: 'Введите название приложения...',
      appName: 'Название приложения:',
      enterAppDescription: 'Введите описание приложения...',
      appDescription: 'Описание приложения:',
      enterFeaturePrice: 'Введите стоимость фич-репорта...',
      featurePrice: 'Cтоимость фич-репорта:',
      enterBugPrice: 'Введите стоимость баг-репорта...',
      bugPrice: 'Стоимость баг-репорта:',
      availableForTesting: 'Доступно для тестирования:',
      enterAppImage: 'Введите ссылку на изображение приложения...',
      appImage: 'Ссылка на изображение приложения:',
      enterDownloadUrl: 'Введите ссылку для скачивания приложения...',
      downloadUrl: 'Ссылка для скачивания приложения:',
      addButton: 'Добавить',
    },
    toast: {
      registerSuccess: 'Вы успешно зарегистрировались',
      changePasswordSuccess: 'Пароль изменен',
      addAppSuccess: 'Приложение добавлено',
      addAppError: 'Ошибка при добавлении приложения',
      appExist: 'Приложение с таким названием уже существует',
      networkError: 'Ошибка сети',
      serverError: 'Ошибка в работе сервера, попробуйте еще раз',
      linkCopied: 'Ссылка скопирована в буфер обмена',
      appChanged: 'Приложение успешно изменено',
      successReport: 'Ваш отчет успешно отправлен',
    },
    landingPage: {
      header: 'Платформа от разработчиков для разработчиков и тестировщиков',
      description: 'Благодаря нам, ваш продукт станет качественнее',
      enterButton: 'Войти',
      firstRole: 'Разработчикам',
      firstDescription:
        'Простая система загрузки, обновления приложения и получения обратной связи от пользователей',
      secondRole: 'Тестировщикам',
      secondDescription:
        'Простая система заключения контракта на тестирование и получения опыта на реальном проекте',
      thirdRole: 'Компаниям',
      thirdDescription:
        'Надежная платформа для найма сотрудников с опытом разработки и тестирования',
    },
  },
};
