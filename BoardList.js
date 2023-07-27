import React, {useState, useEffect} from 'react';
import axios from 'axios';
import styled from 'styled-components';
import BoardListItem from './BoardListItem';
import BoardCellTitle from './BoardCellTitle';

const BoardListBlock = styled.div`
    width: 100%;
`;

const BoardList = () => {
    const [boardList, setBoardList] = useState([]);

    useEffect(() => {
        const getBoardList = async () => {
            try {
                const response = await axios.get('http://localhost:9090/api/board-list');

                console.log(response);
                setBoardList(() => response.data.pageItems.content);
            } catch(error) {
                console.log(error);
            }
        }

        getBoardList();
    }, []);
  return (
    <BoardListBlock>
        <BoardCellTitle></BoardCellTitle>
        {boardList && boardList.map(
            board => (
                <BoardListItem board={board}></BoardListItem>
            )
        )}
    </BoardListBlock>
  );
};

export default BoardList;