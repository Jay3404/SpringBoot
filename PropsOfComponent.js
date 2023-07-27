import React from 'react';

//props받는 방식
//1. 한 번에 하나의 변수로 받기
// const PropsOfComponent = (props) => {
//     return (
//         <>
//             <div>
//                 {props.num}
//             </div>
//             <div>
//                 {props.name}
//             </div>
//         </>
//     );
// }

//2. 비구조할당으로 받기
const PropsOfComponent = ({num, name, propsFunc}) => {
    return (
        <>
            <div>
                {num}
            </div>
            <div>
                {name}
            </div>
            <button onClick={propsFunc}>클릭</button>
        </>
    );
}

//다른 컴포넌트에서 해당 컴포넌트를 사용하려면
//내보내는 코드를 작성해야 import로 불러와서 사용할 수 있다.
export default PropsOfComponent;