import React, { FC, useState } from 'react';
import { Form, InputGroup, Button } from 'react-bootstrap';
import { AiOutlineSearch } from 'react-icons/ai';

interface SearchFieldProps {
  setFilter: React.Dispatch<React.SetStateAction<string>>;
}

const SearchField: FC<SearchFieldProps> = ({ setFilter }) => {
  const [search, setSearch] = useState('');
  return (
    <Form
      className="my-3 m-auto"
      onSubmit={(event) => {
        event.preventDefault();
        setFilter(search);
      }}
    >
      <InputGroup>
        <Form.Control
          type="search"
          className="ps-5 py-2"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
        <Button variant="outline-light" type="submit">
          <AiOutlineSearch />
        </Button>
      </InputGroup>
    </Form>
  );
};

export default SearchField;
