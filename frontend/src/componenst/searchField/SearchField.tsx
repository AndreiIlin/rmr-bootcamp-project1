import React, { FC, useState } from 'react';
import { Button, Form, InputGroup } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { AiOutlineSearch } from 'react-icons/ai';

interface SearchFieldProps {
  setFilter: React.Dispatch<React.SetStateAction<string>>;
}

const SearchField: FC<SearchFieldProps> = ({ setFilter }) => {
  const { t } = useTranslation();
  const [search, setSearch] = useState('');
  return (
    <Form
      className="col-12 col-md-4 m-auto"
      onSubmit={(event) => {
        event.preventDefault();
        setFilter(search);
      }}
    >
      <InputGroup>
        <Form.Control
          type="search"
          placeholder={t('app.findApp')}
          className="py-2"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
        <Button type="submit" className="main-bg">
          <AiOutlineSearch />
        </Button>
      </InputGroup>
    </Form>
  );
};

export default SearchField;
