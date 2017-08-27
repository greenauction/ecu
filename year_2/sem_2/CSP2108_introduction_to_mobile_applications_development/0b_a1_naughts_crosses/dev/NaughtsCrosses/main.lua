-----------------------------------------------------------------------------------------
--
-- main.lua
--
-----------------------------------------------------------------------------------------

-- add custom package path
package.path = package.path .. "./modules;";

-- imports
require("mobdebug").start(); -- enable zerobranestudio debug
class = require("modules.30log.30log-global"); -- require object oriented library, global scope
local Board = require("Board");
--local Player = require("Player");
--local Ai = require("Ai");
--local Scoreboard = require("Scoreboard")

local EMPTY = 0;
local X = 1;
local O = 2;
local whichTurn = X -- X is starting game

local board = Board:new();
print(board.compartments);
board:draw();

--FILL COMPARTMENT W/ COLOUR WHEN TOUCHED
local function fill(event)
    if event.phase == "began" then
        tap = 0;
        for t = 1, 9 do
            if event.x > board.compartments[t][3] and event.x < board.compartments[t][5] then
                if event.y < board.compartments[t][4] and event.y > board.compartments[t][6] then
                    r = board.d.newRect(board.compartments[t][3], board.compartments[t][6], board.w20, board.h20);
                    r:setFillColor(1, 1, 0);
                    r.anchorX = 0;
                    r.anchorY = 0;
                    -- if board[t][7] == EMPTY then
                    --     board[t][7] = whichTurn;
                    --     whichTurn = whichTurn == X and O or X;
                    -- end
                end
            end
        end
    end
end

Runtime:addEventListener("touch", fill)