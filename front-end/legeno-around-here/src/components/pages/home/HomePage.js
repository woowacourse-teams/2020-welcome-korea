import React, { useEffect, useState } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';

import HomeTopBar from './HomeTopBar';
import Bottom from '../../Bottom';

import { findCurrentPostsFromPage } from '../../api/API';
import { getAccessTokenFromCookie } from '../../../util/TokenUtils';
import { HOME } from '../../../constants/BottomItems';
import Loading from '../../Loading';
import BottomBlank from '../../BottomBlank';
import Container from '@material-ui/core/Container';
import PostItem from '../../PostItem';
import { getMainAreaId } from '../../../util/localStorageUtils';
import EndMessage from '../../EndMessage';

const HomePage = ({ location, history }) => {
  const [page, setPage] = useState(0);
  const [posts, setPosts] = useState([]);
  const [hasMore, setHasMore] = useState(false);
  const [sectorId, setSectorId] = useState('none');
  const [locationParams, setLocationParams] = useState(location.search);

  const accessToken = getAccessTokenFromCookie();
  const setter = { setPage, setPosts, setSectorId, setLocationParams };
  const mainAreaId = getMainAreaId();

  const loadNextPosts = async () => {
    try {
      let selectedSectorId = '';
      if (locationParams.includes('?sectorId=')) {
        selectedSectorId = locationParams.split('?sectorId=')[1];
      } else if (sectorId === 'none') {
        selectedSectorId = '';
      } else {
        selectedSectorId = sectorId;
      }

      const nextPosts = await findCurrentPostsFromPage(page, accessToken, mainAreaId, selectedSectorId, history);
      if (nextPosts.length === 0) {
        setHasMore(false);
        return;
      }
      setPosts(posts.concat(nextPosts));
      setPage(page + 1);
    } catch (error) {
      setHasMore(false);
    }
  };

  useEffect(() => {
    setHasMore(true);
    loadNextPosts();
    // eslint-disable-next-line
  }, [sectorId]);

  return (
    <>
      <HomeTopBar setter={setter} sectorId={sectorId} history={history} />
      <Container>
        <InfiniteScroll
          next={loadNextPosts}
          hasMore={hasMore}
          loader={<Loading />}
          dataLength={posts.length}
          endMessage={<EndMessage message={'모두 읽으셨습니다!'} />}
        >
          {posts.map((post) => (
            <PostItem key={post.id} post={post} history={history} />
          ))}
        </InfiniteScroll>
        <BottomBlank />
      </Container>
      <Bottom selected={HOME} />
    </>
  );
};

export default HomePage;
