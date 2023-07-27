import React, {useState, useCallback, useEffect} from 'react';

function App() {
  const [form, setForm] = useState({username: "", password: ""});
  const [data, setData] = useState({
    array: [],
    uValue: null
  });

  //input의 value가 변경됐을 때 실행될 메소드
  const inputValChange = useCallback((e) => {
    //비구조할당으로 e.target(이벤트가 발생한 DOM 태그)의 속성값 받기
    //name값은 username이나 password
    //value는 input의 value
    const {name, value} = e.target;

    console.log("e.target===========> " + e.target);

    console.log("e.target.name=======> " + name);
    console.log("e.target.value======> " + value);

    setForm({
      ...form,
      ///username: "", password: ""
      [name]: value
      //password input의 값을 변경하면
      //username: "", password: 입력한 값
    });
  });

  useEffect(() => {
    console.log("form=======> " + form);
  }, [form]);

  return (
    <form>
      <input name="username"
             placeholder="id"
             value={form.username}
             onChange={inputValChange}></input>
      <input name="password"
             placeholder="pw"
             value={form.password}
             onChange={inputValChange}></input>
      <button type="submit">등록</button>
    </form>
  );
}

export default App;
