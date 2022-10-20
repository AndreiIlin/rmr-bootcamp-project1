import React, { FC, useState } from 'react';
import { Dropdown } from 'react-bootstrap';
import DropdownMenu from 'react-bootstrap/DropdownMenu';
import { useTranslation } from 'react-i18next';

const LanguageSwitcher: FC = () => {
  const { i18n } = useTranslation();

  const [language, setLanguage] = useState('RU');

  const handleLanguage = async (eventKey: string | null) => {
    if (eventKey) {
      await i18n.changeLanguage(eventKey);
      setLanguage(eventKey?.toUpperCase());
    }
  };

  return (
    <Dropdown onSelect={handleLanguage} className="me-3">
      <Dropdown.Toggle variant="dark">{language}</Dropdown.Toggle>
      <DropdownMenu variant="dark" align="end">
        <Dropdown.Item eventKey="ru">RU</Dropdown.Item>
        <Dropdown.Item eventKey="en">EN</Dropdown.Item>
      </DropdownMenu>
    </Dropdown>
  );
};

export default LanguageSwitcher;
