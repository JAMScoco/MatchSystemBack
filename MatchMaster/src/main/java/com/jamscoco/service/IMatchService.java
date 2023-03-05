package com.jamscoco.service;

import java.util.List;
import com.jamscoco.domain.Match;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jamscoco.dto.MatchFileDto;
import com.jamscoco.vo.FileInfoVo;
import com.jamscoco.vo.IndexInfoVo;

/**
 * 赛事Service接口
 *
 * @author jamscoco
 * @date 2022-09-20
 */
public interface IMatchService extends IService<Match>
{
    /**
     * 查询历史赛事列表（不包括当前正在进行的赛事）
     *
     * @param match 赛事
     * @return 赛事集合（不包括当前正在进行的赛事）
     */
    public List<Match> selectHistoryMatchList(Match match);

    /**
     * 获取当前正在进行的赛事
     * @return 当前正在进行的赛事 或 null
     */
    Match getCurrentMatch();

    /**
     * 获取首页信息
     * @param currentMatch
     * @return
     */
    IndexInfoVo getIndexInfo(Match currentMatch);

    /**
     * 查询大赛相关文件
     * @param match
     * @return
     */
    List<FileInfoVo> getFiles(Match match);

    /**
     * 新增赛事文件
     * @param matchFileDto
     * @return
     */
    boolean addMatchFile(MatchFileDto matchFileDto);

    /**
     * 删除赛事文件
     * @param matchFileDto
     * @return
     */
    boolean delMatchFile(MatchFileDto matchFileDto);

    Integer queryRecommendNum(String matchId, String dept);

    int saveRecommendNum(String matchId, String dept, Integer quota);
}
