import React from 'react';
import styled from 'styled-components';

const BoardCellTitleBlock = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
`;

const BoardTilteCell = styled.div`
    text-align: center;
    font-size: 1.25rem;
    line-height: 1.5;
    font-weight: bold;
    background: gray;
    border: 1px solid black;
`;

const BoardCellTitle = () => {
  return (
    <BoardCellTitleBlock>
        <BoardTilteCell style={{width: '100px'}}>번호</BoardTilteCell>
        <BoardTilteCell style={{width: '350px'}}>제목</BoardTilteCell>
        <BoardTilteCell style={{width: '100px'}}>작성자</BoardTilteCell>
        <BoardTilteCell style={{width: '300px'}}>작성일</BoardTilteCell>
        <BoardTilteCell style={{width: '100px'}}>조회수</BoardTilteCell>
    </BoardCellTitleBlock>
  );
};

export default BoardCellTitle;