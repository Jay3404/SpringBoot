import React, { useMemo, useState } from 'react'

//연산 메소드 정의
const getAvg = (numList) => {
    if(numList.length === 0) {
        return 0;
    }

    let sum = 0;

    numList.forEach(num => {
        sum += num;
    });

    return sum / numList.length;
}

const MemoOfComponent = () => {
    const [list, setList] = useState([]);
    const [num, setNum] = useState('');

    const changeNum = (e) => {
        setNum(e.target.value);
    }

    const addList = () => {
        //불변성을 유지하기 위함
        //불변성이라는 것은 원본 데이터의 훼손을 방지하는 것이다.
        const newList = list.concat(parseInt(num));

        //아래의 방식으로도 불변성 유지가능
        // const newList = [...list];
        // newList = newList.concat(num);
        setList(newList);
        setNum('');
    }

    //useMemo를 통한 연산 최적화
    //list 값이 변경됐을 때만 연산이 일어나도록 설정
    const average = useMemo(() => getAvg(list), [list]);

  return (
    <div>
        <input value={num} onChange={changeNum}></input>
        <button onClick={addList}>추가</button>
        <div>
            {average}
        </div>
    </div>
  )
}

export default MemoOfComponent