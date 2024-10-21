package bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import dao.JogoDAO;
import entidades.Jogo;
import util.MessageUtil;

@ManagedBean
@ViewScoped
public class JogoBean {

    private Integer v1;
    private Integer v2;
    private Integer v3;
    private Integer v4;
    private Integer v5;
    private Integer maiorValor; 

    private Jogo jogo = new Jogo();
    private List<Jogo> listaJogos;
    private JogoDAO jogoDAO = new JogoDAO();

    public Integer getV1() { return v1; }
    public void setV1(Integer v1) { this.v1 = v1; }

    public Integer getV2() { return v2; }
    public void setV2(Integer v2) { this.v2 = v2; }

    public Integer getV3() { return v3; }
    public void setV3(Integer v3) { this.v3 = v3; }

    public Integer getV4() { return v4; }
    public void setV4(Integer v4) { this.v4 = v4; }

    public Integer getV5() { return v5; }
    public void setV5(Integer v5) { this.v5 = v5; }

    public Integer getMaiorValor() { return maiorValor; }
    public void setMaiorValor(Integer maiorValor) { this.maiorValor = maiorValor; }

    public void salvar() {
        if (valoresValidos()) {
            Jogo jogo = criarJogo();
            JogoDAO dao = new JogoDAO();
            dao.salvar(jogo);
            mostrarMensagem("Sucesso", "Jogo cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
            limparCampos();
            listaJogos = null;
        } else {
            mostrarMensagem("Erro", "Os valores devem estar entre 1 e 10.", FacesMessage.SEVERITY_ERROR);
        }
    }

    public void excluirJogo(Jogo jogo) {
        try {
            JogoDAO dao = new JogoDAO();
            dao.excluir(jogo.getId());
            listaJogos.remove(jogo);
            mostrarMensagem("Sucesso", "Jogo excluído com sucesso.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            mostrarMensagem("Erro", "Erro ao excluir o jogo.", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
        }
    }

    private Jogo criarJogo() {
        Jogo jogo = new Jogo();
        jogo.setDataCadastro(new Date());
        jogo.setNumeroSorteado(new Random().nextInt(10) + 1);
        jogo.setV1(v1);
        jogo.setV2(v2);
        jogo.setV3(v3);
        jogo.setV4(v4);
        jogo.setV5(v5);
        return jogo;
    }

    public List<Jogo> getListaJogos() {
        if (listaJogos == null) {
            JogoDAO dao = new JogoDAO();
            listaJogos = dao.listar();
        }
        return listaJogos;
    }

    public void editar(RowEditEvent<Jogo> event) {
        try {
            Jogo jogo = event.getObject();
            jogoDAO.editar(jogo);
            MessageUtil.addInfoMsg("Sucesso", "Jogo editado com sucesso");
        } catch (Exception e) {
            MessageUtil.addErrorMsg("Erro", "Erro ao editar o Jogo");
        }
    }

    public void cancelar(RowEditEvent<Jogo> event) {
        MessageUtil.addInfoMsg("Cancelado", "Edição cancelada");
    }

   
    public void acharMaior(Jogo jogo) {
        if (jogo != null) {
            maiorValor = Math.max(jogo.getV1(),
                          Math.max(jogo.getV2(),
                          Math.max(jogo.getV3(),
                          Math.max(jogo.getV4(), jogo.getV5()))));
            
            mostrarMensagem("Maior Valor", "O maior valor é: " + maiorValor, FacesMessage.SEVERITY_INFO);
        }
    }

    private boolean valoresValidos() {
        return v1 != null && v1 >= 1 && v1 <= 10 && 
               v2 != null && v2 >= 1 && v2 <= 10 && 
               v3 != null && v3 >= 1 && v3 <= 10 &&
               v4 != null && v4 >= 1 && v4 <= 10 && 
               v5 != null && v5 >= 1 && v5 <= 10;
    }

    private void limparCampos() {
        v1 = null;
        v2 = null;
        v3 = null;
        v4 = null;
        v5 = null;
    }

    private void mostrarMensagem(String titulo, String mensagem, FacesMessage.Severity severidade) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidade, titulo, mensagem));
    }
}
