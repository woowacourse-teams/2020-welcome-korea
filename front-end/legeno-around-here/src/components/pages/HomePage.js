import React, { useState, useEffect } from 'react';
import AppBar from '../AppBar';
import Bottom from '../Bottom';

import { getAllCurrentPosts } from '../api/API';
import { getAccessTokenFromCookie } from '../../util/TokenUtils';
import PostItem from '../post/PostItem';

const HomePage = () => {
  const [page, setPages] = useState(1);
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const accessToken = getAccessTokenFromCookie();
    const loadPosts = async () => {
      setLoading(true);
      const allPosts = await getAllCurrentPosts(page, accessToken);
      console.log(allPosts);
      setPosts(allPosts);
      setLoading(false);
    };
    loadPosts();
  }, [page]);

  return (
    <>
      <AppBar />
      {posts && posts.map((post) => <PostItem key={post.id} post={post} />)}
      <Bottom />
      {loading && <div>Loading ...</div>}
    </>
  );
};

export default HomePage;
