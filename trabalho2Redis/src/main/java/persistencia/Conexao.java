/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author luizd
 */
public class Conexao {

    private JedisPool pool;
    private Jedis jedis;

    public Conexao() {
        this.pool = new JedisPool("localhost", 6379);
        this.jedis = pool.getResource();
    }

    public Jedis getConexao() {
        return this.jedis;
    }

    public void fechar() {
        this.jedis.close();

        this.pool.close();
    }

}
